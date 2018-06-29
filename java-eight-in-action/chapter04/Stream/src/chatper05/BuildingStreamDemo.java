package chatper05;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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


  }
}
