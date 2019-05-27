package introduce;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ReduceDemo {
  public static void main(String[] args) {
    List<Integer> numbers = Arrays.asList(1,2,3,4,5);
    int product = numbers.stream().reduce(0, Integer::sum);
    System.out.println("Product: " + product);

    Optional<Integer> sum = numbers.stream().reduce(Integer::sum);
    sum.ifPresent(data -> System.out.println("Sum " + data));

    Optional<Integer> max = numbers.stream().reduce(Integer::max);
    max.ifPresent(data -> System.out.println("Max " + data));

    Optional<Integer> min = numbers.stream().reduce(Integer::min);
    min.ifPresent(data -> System.out.println("Min " + data));
  }
}
