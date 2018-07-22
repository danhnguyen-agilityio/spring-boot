package tweetapp.util;

import java.util.ArrayDeque;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * StreamUtil implement util feature relate to Stream
 */
public class StreamUtil {

  /**
   * Reverse elements of a sequential Stream
   * @param stream Stream data
   * @param <T> Generic type
   * @return Stream reversed
   */
  public static <T> Stream<T> reverse(Stream<T> stream) {
//    Collector<T, ArrayDeque<T>, ArrayDeque<T>> collector = Collector.of(() -> new ArrayDeque<T>(),
//        (ArrayDeque<T> result, T element) -> result.addFirst(element),
//        (a, b) -> a);

    Collector<T, ArrayDeque<T>, ArrayDeque<T>> collector = Collector.of(ArrayDeque::new, ArrayDeque::addFirst,
        (a, b) -> a);
    return stream.collect(collector).stream();
  }
}
