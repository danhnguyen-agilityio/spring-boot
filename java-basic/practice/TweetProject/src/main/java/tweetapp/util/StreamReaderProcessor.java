package tweetapp.util;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@FunctionalInterface
public interface StreamReaderProcessor<T> {

  /**
   * Resole with given Stream
   * @param stream Stream lines of file
   * @return List<T>
   */
  List<T> process(Stream<String> stream) throws IOException;
}
