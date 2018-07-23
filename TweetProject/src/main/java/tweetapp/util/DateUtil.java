package tweetapp.util;

import java.time.*;

/**
 * DateUtil class support converting date, check date valid with specific condition
 */
public class DateUtil {

  /**
   * Convert string have timezone to LocalDateTime
   * @param text String formatted date need convert
   * @return local date time
   */
  public static LocalDateTime convertStringToLocalDateTime(String text) {
    return convertStringToLocalDateTime(text, ZoneId.systemDefault());
  }

  /**
   * Convert string have timezone to LocalDateTime with specific zoneId
   * @param text String formatted date need convert
   * @return local date time
   */
  public static LocalDateTime convertStringToLocalDateTime(String text, ZoneId zoneId) {
    text = text.replace("\"", ""); // Remove character " if have
    ZonedDateTime zonedDateTime = ZonedDateTime.parse(text);
    return zonedDateTime.withZoneSameInstant(zoneId).toLocalDateTime();
  }

  /**
   * Test whether dateTime passed method within given period ago
   * @param dateTime Date time need check
   * @param period Period time
   * @return true if dateTime passed method within given period ago
   */
  public static boolean withinNumberDaysAgo(LocalDateTime dateTime, Period period) {
    return withinNumberDaysAgo(dateTime, period, LocalDateTime.now());
  }

  /**
   * Test whether dateTime passed method within given period ago from dateTimeFrom
   * @param dateTime Date time need check
   * @param period Period time
   * @return true if dateTime passed method within given period ago from dateTimeFrom
   */
  public static boolean withinNumberDaysAgo(LocalDateTime dateTime, Period period, LocalDateTime startDateTime) {
    LocalDateTime endOfToday = LocalDateTime.of(startDateTime.toLocalDate(), LocalTime.MAX);
    LocalDateTime beforeNumberDays = endOfToday
        .minusYears(period.getYears())
        .minusMonths(period.getMonths())
        .minusDays(period.getDays());
    return dateTime.compareTo(beforeNumberDays) >= 0;
  }



}
