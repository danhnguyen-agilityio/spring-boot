package chapter08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAround {

  // Functional interface for a lambda, which can throw an IOException
  public interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
  }

  public static String processFile(BufferedReaderProcessor p) throws IOException {
    try(BufferedReader br = new BufferedReader(new FileReader("file"))) {
      return p.process(br);
    }
  }

  public static void main(String[] args) throws IOException {
    String oneLine = processFile((BufferedReader b) -> b.readLine());
    String twoLine = processFile(b -> b.readLine() + b.readLine());
  }
}
