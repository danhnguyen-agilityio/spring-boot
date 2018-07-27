package chapter12;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LocalDateTimeWithTimezones {
  public static void main(String[] args) {
    LocalDateTime localDateTime = LocalDateTime.of(2016, 11, 28, 9, 30);
    System.out.println("Local date time is: " + localDateTime);

    ZonedDateTime LAZone = localDateTime.atZone(ZoneId.of("America/Los_Angeles"));
    System.out.println("LAZone: " + LAZone);

    ZonedDateTime HCMZone = localDateTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
    System.out.println("HCMZone: " + HCMZone);

    ZonedDateTime LADateTimeToUTC = LAZone.withZoneSameInstant(ZoneId.of("UTC+00:00"));
    System.out.println("Converted to UTC time zone:" + LADateTimeToUTC);

    ZonedDateTime LADateTimeToGMTPlus1 = LAZone.withZoneSameInstant(ZoneId.of("GMT+01:00"));
    System.out.println("LADateTimeToGMTPlus1: " + LADateTimeToGMTPlus1);

    ZonedDateTime LADateTimeToGMTPlus2 = LAZone.withZoneSameLocal(ZoneId.of("GMT+01:00"));
    System.out.println("LADateTimeToGMTPlus1: " + LADateTimeToGMTPlus2);
  }
}
