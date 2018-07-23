package tweetapp.util;

import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;

import static org.junit.Assert.*;

public class DateUtilTest {

  /**
   * Test convert string to local date time
   */
  @Test
  public void convertStringToLocalDateTime() {
    String text = "2018-07-16T09:21:45.492Z";
    ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");

    LocalDateTime actual = DateUtil.convertStringToLocalDateTime(text, zoneId);
    LocalDateTime expected = LocalDateTime.parse("2018-07-16T16:21:45.492");

    assertEquals(actual, expected);
  }

  /**
   * Test whether dateTime passed method within given period ago from given fromDate
   */
  @Test
  public void withinNumberDaysAgo() {
    LocalDateTime dateTime = LocalDateTime.parse("2018-07-10T16:21:45");
    LocalDateTime fromDate = LocalDateTime.parse("2018-07-16T10:21:45");

    // Test dateTime within a day from fromDate
    boolean withinToday = DateUtil.withinNumberDaysAgo(dateTime, Period.ofDays(1), fromDate);
    assertEquals(false, withinToday);

    // Test dateTime with a week from fromDate
    boolean withinWeek = DateUtil.withinNumberDaysAgo(dateTime, Period.ofWeeks(1), fromDate);
    assertEquals(true, withinWeek);
  }
}