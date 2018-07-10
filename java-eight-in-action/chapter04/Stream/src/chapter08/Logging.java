package chapter08;

import java.util.Arrays;
import java.util.List;

public class Logging {
  public static void main(String[] args) {
    List<Integer> numbers = Arrays.asList(2,3,4,5);

    numbers.stream()
        .peek(x -> System.out.println(x))
        .map(x -> x + 17)
        .filter(x -> x % 2 == 0)
        .limit(3)
        .forEach(System.out:: println);
  }
}
