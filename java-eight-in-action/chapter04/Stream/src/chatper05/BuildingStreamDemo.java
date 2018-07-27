package chatper05;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BuildingStreamDemo {
  public static void main(String[] args) {
    Stream<String> stream = Stream.of("java", "lambdas", "in", "action");
    stream.map(String::toUpperCase).forEach(System.out::println);

    int[] numbers = {2,3,5,7,11,13};
    int sum = Arrays.stream(numbers).sum();

    long uniqueWords = 0;
    try (Stream<String> lines = Files.lines(Paths.get("./src/chatper05/data.txt"), Charset.defaultCharset())) {
      uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
          .distinct().count();
      System.out.println("Count: " + uniqueWords);
    } catch (IOException e) {
      e.printStackTrace();
    }

    Stream.iterate(0, n -> n +2)
          .limit(10)
          .forEach(System.out::println);

    System.out.println("Fibonacies:");
    Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
        .limit(10)
        .map(t -> t[0])
        .forEach(System.out::println);

    System.out.println("Random: ");
    Stream.generate(Math::random)
        .limit(5)
        .sorted()
        .forEach(System.out::println);

    IntStream.generate(() -> 1);

    IntStream.generate(new IntSupplier() {
      @Override
      public int getAsInt() {
        return 0;
      }
    });

    // create instance of IntSupplier
    IntSupplier fib = new IntSupplier() {
      private int previous = 0;
      private int current = 1;

      @Override
      public int getAsInt() {
        int oldPrevious = this.previous;
        int nextValue = this.previous + this.current;
        this.previous = this.current;
        this.current = nextValue;
        return oldPrevious;
      }
    };
  }
}
