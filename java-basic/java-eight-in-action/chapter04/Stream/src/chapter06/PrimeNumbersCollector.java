package chapter06;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

public class PrimeNumbersCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

  public boolean isPrime(List<Integer> primes, int candidate) {
    int candidateRoot = (int) Math.sqrt(candidate);
    return takeWhile(primes, i -> i <= candidateRoot)
        .stream()
        .noneMatch(p -> candidate % p == 0);
  }

  public <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
    int i = 0;
    for (A item : list) {
      if (!p.test(item)) { // Check if the current item in the list satisfies the Predicate
        return list.subList(0, i); // If it doesn't, return the sublist prefix until the item before the tested one
      }
      i++;
    }
    return list; // All items in the list satisfy the Predicate, so return the list itself
  }

  @Override
  public Supplier<Map<Boolean, List<Integer>>> supplier() {
    return () -> new HashMap<Boolean, List<Integer>>(){{
      put(false, new ArrayList<Integer>());
      put(true, new ArrayList<Integer>());
    }};
  }

  @Override
  public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
    return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
      acc.get(isPrime(acc.get(true), candidate))
          .add(candidate);
    };
  }

  @Override
  public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
    return (Map<Boolean, List<Integer>> acc1, Map<Boolean, List<Integer>> acc2) -> {
      acc1.get(true).addAll(acc2.get(true));
      acc1.get(false).addAll(acc2.get(false));
      return acc1;
    };
  }

  @Override
  public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
    return Function.identity();
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
  }
}
