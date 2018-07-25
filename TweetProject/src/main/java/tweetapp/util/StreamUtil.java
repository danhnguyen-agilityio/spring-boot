package tweetapp.util;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
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

  /**
   * Check given list is sorted or no
   * @param list List need test
   * @param comparator Comparator used to compare
   * @param <T> Generic type
   * @return true if list is sorted or false if other
   */
  public static <T> boolean isSorted(List<T> list, Comparator<T> comparator) {
    return list.stream().sorted(comparator).collect(Collectors.toList()).equals(list);
  }

}
