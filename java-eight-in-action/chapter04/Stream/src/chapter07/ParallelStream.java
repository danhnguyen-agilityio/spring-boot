package chapter07;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStream {

  /**
   * Sum of all the number from 1 to given argument
   */
  public static long sequentialSum(long n) {
    return Stream.iterate(1L, i -> i + 1)
        .limit(n)
        .reduce(0L, Long::sum);
  }

  public static long iterativeSum(long n) {
    long result = 0;
    for (long i = 1L; i <= n; i++) {
      result += i;
    }
    return  result;
  }

  public static long parallelSum(long n) {
    return Stream.iterate(1L, i -> i + 1)
        .limit(n)
        .parallel() // turn the sequential stream into a parallel one
        .reduce(0L, Long::sum);
  }

  public static long rangedSum(long n) {
    return LongStream.rangeClosed(1, n)
        .reduce(0L, Long::sum);
  }

  public static long measureSumPerf(Function<Long, Long> adder, long n) {
    long fastest = Long.MAX_VALUE;
    for (int i = 0; i < 10; i++) {
      long start = System.nanoTime();
      long sum = adder.apply(n);
      long duration = (System.nanoTime() - start) / 1000000;
      System.out.println("Result : " + sum);
      if (duration < fastest) fastest = duration;
    }
    return fastest;
  }

  public static long parallelRangedSum(long n ) {
    return LongStream.rangeClosed(1, n)
        .parallel()
        .reduce(0L, Long::sum);
  }

  public static long sideEffectSum(long n) {
    Accumulator accumulator = new Accumulator();
    LongStream.rangeClosed(1, n).forEach(accumulator::add);
    return accumulator.total;
  }

  public static long sideEffectParallelSum(long n) {
    Accumulator accumulator = new Accumulator();
    LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
    return accumulator.total;
  }

  public static void main(String[] args) {
//    System.out.println(Runtime.getRuntime().availableProcessors());
//    System.out.println("Parallel sum: " + measureSumPerf(ParallelStream::parallelSum, 10000000) + " msecs");
//    System.out.println("Sequential sum: " + measureSumPerf(ParallelStream::sequentialSum, 10000000) + " msecs");
    System.out.println("Iterative sum: " + measureSumPerf(ParallelStream::iterativeSum, 10000000) + " msecs");
    System.out.println("Ranged sum: " + measureSumPerf(ParallelStream::rangedSum, 10000000) + " msecs");
    System.out.println("Parallel Ranged sum: " + measureSumPerf(ParallelStream::parallelRangedSum, 10000000) + " msecs");

    System.out.println("sideEffectSum: " + measureSumPerf(ParallelStream::sideEffectSum, 10000000) + " msecs");
    System.out.println("sideEffectSum: " + measureSumPerf(ParallelStream::sideEffectParallelSum, 10000000) + " msecs");
  }
}

class Accumulator {
  public long total = 0;
  public void add(long value) {
    total += value;
  }
}