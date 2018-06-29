package introduce;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamDemo {
  public static void main(String[] args) {
    List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
    Optional<Integer> firstSquareDivisibleByThree = someNumbers.stream()
        .map(x -> x * x)
        .filter(x -> x % 3 == 0)
        .findFirst();
    System.out.print("First square divisible by three: ");
    firstSquareDivisibleByThree.ifPresent(System.out::println);

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

    int caloriesIntStream = menu.stream()
        .mapToInt(Dish::getCalories)
        .sum();
    System.out.println("caloriesIntStream : " + caloriesIntStream);

    OptionalInt max = menu.stream().mapToInt(Dish::getCalories).max();

    IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
    Stream<Integer> stream = intStream.boxed();

    int calories = menu.stream()
        .map(Dish::getCalories)
        .reduce(0, Integer::sum);

    if (menu.stream().anyMatch(Dish::isVegetarian)) {
      System.out.println("Vegetarian friendly");
    }

    boolean isHealthy = menu.stream().allMatch(d -> d.getCalories() < 1000);

    menu.stream().filter(Dish::isVegetarian).findAny()
        .ifPresent(d -> System.out.println("Demo ifPresent " + d.getName()));

    List<String> threeHighCaloricDishNames =
        menu.stream() // get a stream from menu
            .filter(d -> {
              System.out.println("filtering " + d.getName());
              return d.getCalories() > 300;
            })
            .map(d -> {
              System.out.println("mapping " + d.getName());
              return d.getName();
            }) // Get the names of the dishes
            .limit(3) // select only the first three
            .collect(Collectors.toList()); // store the results in another list

    System.out.println(threeHighCaloricDishNames);

    // external iteration
    List<String> names = new ArrayList<>();
    Iterator<Dish> iterator = menu.iterator();
    while (iterator.hasNext()) {
      Dish d = iterator.next();
      names.add(d.getName());
    }

    // internal iteration
    names = menu.stream()
        .map(Dish::getName)
        .collect(Collectors.toList());

    List<String> title = Arrays.asList("java8", "In", "action");
    Stream<String> s = title.stream();
    s.forEach(System.out::println);
//        s.forEach(System.out::println); // throw an exception

    long count = menu.stream().count();

    // demo use Arrays.stream
    String[] arrayOfWords = {"goodbye", "world"};
    Stream<String> streamOfwords = Arrays.stream(arrayOfWords);

    List<String> words = Arrays.asList(arrayOfWords);

    Stream<String[]> result = words.stream()
        .map(word -> word.split(""));

    Stream<String> result1 = result.flatMap(Arrays::stream);
    result1.distinct().forEach(System.out::print);

    List<Integer> number1 = Arrays.asList(1, 2, 3);
    List<Integer> number2 = Arrays.asList(3, 4);
    List<int[]> pairs = number1.stream()
        .flatMap(i -> number2.stream().filter(j -> (i + j) % 3 == 0).map(j -> new int[]{i, j}))
        .collect(Collectors.toList());

  }
}
