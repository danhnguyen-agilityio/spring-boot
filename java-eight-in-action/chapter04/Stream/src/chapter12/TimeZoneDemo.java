package chapter12;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeZoneDemo {
  public static void main(String[] args) {
    ZoneId romeZone = ZoneId.of("Europe/Rome");
    LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
    ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
    System.out.println(zdt1);

    LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
    ZonedDateTime zdt2 = dateTime.atZone(romeZone);
    System.out.println(zdt2);

    Instant instant = Instant.now();
    ZonedDateTime zdt3 = instant.atZone(romeZone);
    System.out.println(zdt3);

//    LocalDateTime dateTime1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
//    Instant instant1 = dateTime1.toInstant(romeZone);
//    System.out.println(instant1);

    LocalDateTime timeFromInstant = LocalDateTime.ofInstant(instant, romeZone);
    System.out.println(timeFromInstant);

    ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
    LocalDateTime dateTime1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
    OffsetDateTime dateTimeInNewYork = OffsetDateTime.of(dateTime1, newYorkOffset);
    System.out.println(dateTimeInNewYork);
  }
}
