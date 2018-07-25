package tweetapp.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * RandomUtil class
 */
public class RandomUtil {
  private static Random random = new Random();

  /**
   * Random string
   * @return String with length is 8
   */
  public static String randomString() {
    int length = 8;
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        + "abcdefghijklmnopqrstuvwxyz"
        + "0123456789";
    return random
        .ints(length, 0, chars.length())
        .mapToObj(i -> "" + chars.charAt(i))
        .collect(Collectors.joining());
  }

  /**
   * Random datetime between two LocalDateTime
   * @param start Date time start
   * @param end Date time end
   * @return LocalDateTime between start and end
   */
  public static LocalDateTime randomDateTimeBetween(LocalDateTime start, LocalDateTime end) {
    Duration duration = Duration.between(start, end);
    long seconds = duration.getSeconds();
    return start.plusSeconds(ThreadLocalRandom.current().nextLong(seconds - 1) + 1);
  }

  /**
   * Random datetime before given dateTime
   * @param dateTime LocalDateTime
   * @return LocalDateTime before given dateTime
   */
  public static LocalDateTime randomDateTimeBefore(LocalDateTime dateTime) {
    return dateTime.minusSeconds(Math.abs(random.nextInt()) + 1);
  }
}
