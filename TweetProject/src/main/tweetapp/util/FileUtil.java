package tweetapp.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileUtil {

  /**
   * Read file and return data by bufferedReaderProcessor passed
   * @param fileName FileUtil name to read
   * @param bufferedReaderProcessor
   * @param <T>
   * @return data returned by method process
   * @throws IOException
   */
  public static  <T> List<T> readFile(String fileName, BufferedReaderProcessor<T> bufferedReaderProcessor) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      return bufferedReaderProcessor.process(br);
    }
  }
}
