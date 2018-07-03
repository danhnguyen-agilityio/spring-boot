package chatper05;

import introduce.Dish;

import java.util.*;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

public class GroupingDemo {
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

    Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
    System.out.println("dishesByType :" + dishesByType);

    Map<Dish.CaloricLevel, List<Dish>> dishedByCaloricLevel = menu.stream().collect(
        groupingBy(dish -> {
          if (dish.getCalories() <= 400) return Dish.CaloricLevel.DIET;
          else if (dish.getCalories() <= 700) return Dish.CaloricLevel.NORMAL;
          else return Dish.CaloricLevel.FAT;
        })
    );
    System.out.println("dishedByCaloricLevel :" + dishedByCaloricLevel);

    Map<Dish.Type, Map<Dish.CaloricLevel,List<Dish>>> dishesByTypeCaloricLevel = menu.stream().collect(groupingBy(Dish::getType,
        groupingBy(dish -> {
          if (dish.getCalories() <= 400) return Dish.CaloricLevel.DIET;
          else if (dish.getCalories() <= 700) return Dish.CaloricLevel.NORMAL;
          else return Dish.CaloricLevel.FAT;
        })));
    System.out.println("dishesByTypeCaloricLevel :" + dishedByCaloricLevel);

    Map<Dish.Type, Long> typesCount = menu.stream().collect(groupingBy(Dish::getType, counting()));
    System.out.println("typesCount: " + typesCount);

    Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream().collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
    System.out.println("mostCaloricByType : " + mostCaloricByType);

    Map<Dish.Type, Dish> mostCaloriesByType2 = menu.stream().collect(groupingBy(Dish::getType, collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
    System.out.println("mostCaloriesByType2 : " + mostCaloriesByType2);

    Map<Dish.Type, Integer> totalCaloriesByType = menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
    System.out.println("totalCaloriesByType: " + totalCaloriesByType);

    Map<Dish.Type, Set<Dish.CaloricLevel>> caloricLevelsByType = menu.stream().collect(groupingBy(Dish::getType, mapping(dish -> {
      if (dish.getCalories() <= 400) return Dish.CaloricLevel.DIET;
      else if (dish.getCalories() <= 700) return Dish.CaloricLevel.NORMAL;
      else return Dish.CaloricLevel.FAT;
    }, toSet())));
    System.out.println("caloricLevelsByType " + caloricLevelsByType);

    Map<Dish.Type, Set<Dish.CaloricLevel>> caloricLevelsByType2 = menu.stream().collect(groupingBy(Dish::getType, mapping(dish -> {
      if (dish.getCalories() <= 400) return Dish.CaloricLevel.DIET;
      else if (dish.getCalories() <= 700) return Dish.CaloricLevel.NORMAL;
      else return Dish.CaloricLevel.FAT;
    }, toCollection(HashSet::new))));
    System.out.println("caloricLevelsByType2 " + caloricLevelsByType2);

  }
}
