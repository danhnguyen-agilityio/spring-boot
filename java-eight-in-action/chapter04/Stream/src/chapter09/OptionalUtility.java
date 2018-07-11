package chapter09;

import java.util.Optional;
import java.util.Properties;

public class OptionalUtility {
  public static Optional<Integer> stringToInt(String s) {
    try {
      return Optional.of(Integer.parseInt(s));
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }

  public int readDuration(Properties properties, String name) {
    return Optional.ofNullable(properties.getProperty(name))
        .flatMap(OptionalUtility::stringToInt)
        .filter(i -> i > 0)
        .orElse(0);
  }
}
