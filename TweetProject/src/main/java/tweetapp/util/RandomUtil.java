package tweetapp.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
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

  /**
   * Random month of year
   * @return Month of year
   */
  public static int randomMonthOfYear() {
    return random.nextInt(12) + 1; // random from 1 -> 12
  }

  /**
   * Random date before today
   * @return LocalDate
   */
  public static LocalDate randomDate() {
    LocalDate start = LocalDate.MIN;
    long days = Period.between(start, LocalDate.now()).getDays();
    LocalDate randomeDate = start.plusDays(random.nextInt((int) days + 1));
    return randomeDate;
  }

  /**
   * Random date from age
   * @param age Age of user
   * @return Date time have given age
   */
  public static LocalDateTime randomDateFromAge(int age) {
    return randomDateTimeBetween(AgeCalculator.minDate(age).atStartOfDay(),
        AgeCalculator.maxDate(age).atStartOfDay());
  }

}
