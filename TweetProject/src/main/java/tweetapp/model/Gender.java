package tweetapp.model;

import java.util.Random;

/**
 * Enum Gender
 */
public enum Gender {
  OTHER, MALE, FEMALE;

  public static final Gender[] VALUES = values();
  public static final int SIZE = VALUES.length;
  private static final Random RANDOM = new Random();

  public static Gender getRandom() {
    return VALUES[RANDOM.nextInt(SIZE)];
  }
}
