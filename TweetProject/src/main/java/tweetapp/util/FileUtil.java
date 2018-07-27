package tweetapp.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FileUtil {

  /**
   * Read file and return data by bufferedReaderProcessor passed
   * @param fileName FileUtil name to read
   * @param bufferedReaderProcessor BufferedReaderProcessor
   * @param <T> Generic type
   * @return data returned by method process
   * @throws IOException occur when file not found
   */
  public static  <T> List<T> readFile(String fileName, BufferedReaderProcessor<T> bufferedReaderProcessor)
      throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      return bufferedReaderProcessor.process(br);
    }
  }

  /**
   * Read file and return data by bufferedReaderProcessor passed
   * @param fileName FileUtil name to read
   * @param streamReaderProcessor StreamReaderProcessor
   * @param <T> Generic type
   * @return data returned by method process
   * @throws IOException occur when file not found
   */
  public static <T> List<T> readFile(String fileName, StreamReaderProcessor<T> streamReaderProcessor)
      throws IOException {
    try (Stream<String> lines = Files.lines(Paths.get(fileName), Charset.defaultCharset())) {
      return streamReaderProcessor.process(lines);
    }
  }

}
