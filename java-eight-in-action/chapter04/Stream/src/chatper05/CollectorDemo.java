package chatper05;

import introduce.Dish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectorDemo {
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

    List<Dish> dishes = menu.stream().collect(new ToListCollector<>());
    System.out.println("dishes: " + dishes);

    List<Dish> dishes1 = menu.stream().collect(ArrayList::new, List::add, List::addAll);
    System.out.println("dished: " + dishes1);
  }
}
