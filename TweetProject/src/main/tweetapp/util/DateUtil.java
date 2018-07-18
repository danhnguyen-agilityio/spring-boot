package tweetapp.util;

import java.time.*;

public class DateUtil {

  /**
   * Convert string have timezone to LocalDateTime
   * @param text
   * @return local date time
   */
  public static LocalDateTime convertStringToLocalDateTime(String text) {
    text = text.replace("\"", ""); // Remove character " if have
    ZonedDateTime zonedDateTime = ZonedDateTime.parse(text);
    ZoneId defaultTimeZone = ZoneId.systemDefault();
    return zonedDateTime.withZoneSameInstant(defaultTimeZone).toLocalDateTime();
  }

  // FIXME:: change name method in here
  public static boolean withinNumberDaysAgo(LocalDateTime dateTime, int days) {
    LocalDateTime endOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    LocalDateTime beforeNumberDays = endOfToday.minusDays(days);
    return dateTime.compareTo(beforeNumberDays) >= 0;
  }


}
