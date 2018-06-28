package introduce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {
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

    }
}
