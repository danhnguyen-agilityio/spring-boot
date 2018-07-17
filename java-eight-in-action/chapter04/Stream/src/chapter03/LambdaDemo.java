package chapter03;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Function;

@FunctionalInterface
interface BufferedReaderProcessor {
  String process(BufferedReader br) throws IOException;
}

public class LambdaDemo {
  public static void main(String[] args) {
    Function<BufferedReader, String> f =
        br -> {
          try {
            return br.readLine();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        };

    BufferedReaderProcessor bufferedReaderProcessor = br -> br.readLine();
  }
}
