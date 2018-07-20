package tweetapp.util;

import java.time.*;
import java.util.Date;

public class DateUtil {

  /**
   * Convert string have timezone to LocalDateTime
   * @param text
   * @return local date time
   */
  public static LocalDateTime convertStringToLocalDateTime(String text) {
    return convertStringToLocalDateTime(text, ZoneId.systemDefault());
  }

  /**
   * Convert string have timezone to LocalDateTime with specific zoneId
   * @param text
   * @return local date time
   */
  public static LocalDateTime convertStringToLocalDateTime(String text, ZoneId zoneId) {
    text = text.replace("\"", ""); // Remove character " if have
    ZonedDateTime zonedDateTime = ZonedDateTime.parse(text);
    return zonedDateTime.withZoneSameInstant(zoneId).toLocalDateTime();
  }

  // FIXME:: change name method in here
  /**
   * Test whether dateTime passed method within given period ago
   * @param dateTime
   * @param period
   * @return true if dateTime passed method within given period ago
   */
  public static boolean withinNumberDaysAgo(LocalDateTime dateTime, Period period) {
    return withinNumberDaysAgo(dateTime, period, LocalDate.now());
  }

  /**
   * Test whether dateTime passed method within given period ago
   * @param dateTime
   * @param period
   * @return true if dateTime passed method within given period ago
   */
  public static boolean withinNumberDaysAgo(LocalDateTime dateTime, Period period, LocalDate checkDateTime) {
    LocalDateTime endOfToday = LocalDateTime.of(checkDateTime, LocalTime.MAX);
    LocalDateTime beforeNumberDays = endOfToday
        .minusYears(period.getYears())
        .minusMonths(period.getMonths())
        .minusDays(period.getDays());
    return dateTime.compareTo(beforeNumberDays) >= 0;
  }



}
