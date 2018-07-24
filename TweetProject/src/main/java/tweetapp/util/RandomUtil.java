package tweetapp.util;

import java.util.Random;
import java.util.stream.Collectors;

/**
 * RandomUtil class
 */
public class RandomUtil {

  /**
   * Random string
   * @return String with length is 8
   */
  public static String randomString() {
    int length = 8;
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        + "abcdefghijklmnopqrstuvwxyz"
        + "0123456789";
    return new Random()
        .ints(length, 0, chars.length())
        .mapToObj(i -> "" + chars.charAt(i))
        .collect(Collectors.joining());
  }
}
