package tweetapp.util;

import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;

public class DateUtilTest {

  /**
   * Test convert string to local date time
   */
  @Test
  public void testConvertStringToLocalDateTime() {
    String text = "2018-07-16T09:21:45.492Z";
    ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");

    LocalDateTime actual = DateUtil.convertStringToLocalDateTime(text, zoneId);
    LocalDateTime expected = LocalDateTime.parse("2018-07-16T16:21:45.492");

    assertEquals(actual, expected);
  }

  // FIXME:: Revise method withinNumberDaysAgo to implement testing
  @Ignore
  @Test
  public void textWithinNumberDaysAgo() {
    int days = 7;
    LocalDateTime localDateTime = LocalDateTime.parse("2018-07-16T16:21:45");
    boolean actual = DateUtil.withinNumberDaysAgo(localDateTime, days);
    assertEquals(true, actual);
  }
}