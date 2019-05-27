package chapter07;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * Extend RecursiveTask to create a task usable with fork/join framework
 */
public class ForkJoinSumCalculator extends RecursiveTask {

  private final long[] number; // The array of numbers to be summed
  private final int start; // The initial and final positions of the portion of the array processed by this subtask
  private final int end;

  public static final long THRESHOLD = 10000; // The size of the array under which this task is no longer split into subtasks

  /**
   * Public constructor used to create the main task
   */
  public ForkJoinSumCalculator(long[] number, int start, int end) {
    this.number = number;
    this.start = start;
    this.end = end;
  }

  /**
   * Private constructor used to recursively create subtasks of the main task
   */
  private ForkJoinSumCalculator(long[] numbers) {
    this(numbers, 0, numbers.length);
  }

  @Override
  protected Long compute() {
    int length = end - start; // The size of the portion of the array summed by this task
    if (length <= THRESHOLD) {
      return computeSequentially(); // If the size is less than or equal to the threshold, compute the result sequentially
    }

    // Create a subtask to sum the first half of the array
    ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(number, start, start + length/2);
    // Asynchronously execute the newly created subtask using another thread of the ForkJoinPool
    leftTask.fork();

    // Create a subtask to sum the second half of the array
    ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(number, start + length/2, end);
    // Execute this second subtask synchronously, potentially allowing further recursive splits
    Long rightResult = rightTask.compute();

    // Read the result of the first subtask or wait for it if it isn't ready
    Long leftResult = (Long) leftTask.join();
    return leftResult + rightResult;

  }

  /**
   * Calculating the result of a subtask when it's no longer divisible
   */
  private long computeSequentially() {
    long sum = 0;
    for (int i = start; i < end; i++) {
      sum += number[i];
    }
    return sum; // The result of this task is the combination of the result of the two subtask
  }

  public static long forkJoinSum(long n) {
    long[] numbers = LongStream.rangeClosed(1, n).toArray();
    ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
    return new ForkJoinPool().invoke(task);
  }
}
