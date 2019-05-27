package chatper05;

import introduce.Dish;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class PartitioningDemo {
  public static void main(String[] args) {
    List<Dish> menu = Arrays.asList(
        new Dish("prawns", false, 300, Dish.Type.FISH),
        new Dish("pork", false, 800, Dish.Type.MEAT),
        new Dish("beef", false, 700, Dish.Type.MEAT),
        new Dish("chicken", false, 400, Dish.Type.MEAT),
        new Dish("french fries", true, 530, Dish.Type.OTHER),
        new Dish("rice", true, 350, Dish.Type.OTHER),
        new Dish("season fruit", true, 120, Dish.Type.OTHER),
        new Dish("pizza", true, 550, Dish.Type.OTHER),
        new Dish("salmon", false, 450, Dish.Type.FISH)
    );

    Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(partitioningBy(Dish::isVegetarian));
    List<Dish> vegetarianDishes = partitionedMenu.get(true);
    System.out.println("partitionedMenu :" + partitionedMenu);

    List<Dish> vegetarianDishes2 = menu.stream().filter(Dish::isVegetarian).collect(toList());

    Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
    System.out.println("vegetarianDishesByType " + vegetarianDishesByType);

    Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = menu.stream().collect(partitioningBy(Dish::isVegetarian, collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
    System.out.println("mostCaloricPartitionedByVegetarian : " + mostCaloricPartitionedByVegetarian);

    Map<Boolean, Map<Boolean, List<Dish>>> vegetarianCaloriDishes = menu.stream().collect(partitioningBy(Dish::isVegetarian, partitioningBy(d -> d.getCalories() > 500)));
    System.out.println("vegetarianCaloriDishes :" + vegetarianCaloriDishes);

    Map<Boolean, Long> countVegetarian = menu.stream().collect(partitioningBy(Dish::isVegetarian, counting()));
    System.out.println("countVegetarian :" + countVegetarian);

    Map<Boolean, List<Integer>> partitionedPrimes = partitionPrimes(30);
    System.out.println("partitionedPrimes : " + partitionedPrimes);

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
