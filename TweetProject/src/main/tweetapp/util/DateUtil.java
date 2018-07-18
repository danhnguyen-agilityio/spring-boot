package tweetapp.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
}
