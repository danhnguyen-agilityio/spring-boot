package tweetapp.util;

import java.time.*;

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
   * Test whether dateTime passed method within specific days ago
   * @param dateTime
   * @param days
   * @return true if dateTime passed method within specific days ago
   */
  public static boolean withinNumberDaysAgo(LocalDateTime dateTime, int days) {
    LocalDateTime endOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    LocalDateTime beforeNumberDays = endOfToday.minusDays(days);
    return dateTime.compareTo(beforeNumberDays) >= 0;
  }


}
