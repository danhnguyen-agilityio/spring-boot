package chapter06;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;

public class CustomCollectorDemo {
  public static void main(String[] args) {
    Map<Boolean, List<Integer>> partitionPrimes1 = partitionPrimesWithCustomCollector(30);
    System.out.println(partitionPrimes1);

    long fastest = Long.MAX_VALUE;
    long start = System.nanoTime();
    for (int i = 0; i < 10; i++) {
      partitionPrimes(1000);
    }
    long duration = System.nanoTime() - start;
    System.out.println("partitionPrimes : " + duration);


    start = System.nanoTime();
    for (int i = 0; i < 10; i++) {
      partitionPrimesWithCustomCollector(1000);
    }
    duration = System.nanoTime() - start;
    System.out.println("partitionPrimesWithCustomCollector : " + duration);
  }

  public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
    return IntStream.rangeClosed(2, n).boxed().collect(new PrimeNumbersCollector());
  }

  public static boolean isPrime(int candidate) {
//    return IntStream.range(2, candidate).noneMatch(i -> candidate % i == 0);
    int candidateRoot = (int) Math.sqrt(candidate);
    return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
  }

  public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
    return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(candidate -> isPrime(candidate)));
  }

}
