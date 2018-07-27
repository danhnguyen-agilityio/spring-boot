package chapter12;

import java.sql.Time;
import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.stream.Collectors;

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

    ZonedDateTime zonedDateTime = ZonedDateTime.parse("1974-01-15T12:47:20.430Z");
    System.out.println("Convert string to ZonedDatetime: " + zonedDateTime);

    LocalDateTime localDateTime1 = LocalDateTime.ofInstant(zonedDateTime.toInstant(), TimeZone.getDefault().toZoneId());
    System.out.println("Convert timezone to LocalDateTime: way 1 " + localDateTime1);

    LocalDateTime localDateTime2 = zonedDateTime.withZoneSameInstant(TimeZone.getDefault().toZoneId()).toLocalDateTime();
    System.out.println("Convert timezone to LocalDateTime: way 2 " + localDateTime2);

    LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    System.out.println("Today: " + now);

    LocalDateTime endOfDay = now.with(LocalDateTime.MAX);
    endOfDay.withMonth(1);
    System.out.println("End of today: " + endOfDay.withMonth(1));

    System.out.println(Duration.between(LocalDateTime.parse("1974-01-15T12:47:20.430"), LocalDateTime.parse("1974-01-14T12:47:20.430"))
    .getSeconds());

    Random random = new Random();
    System.out.println(random.nextInt(2));

    LocalDate currentDate = LocalDate.of(2017, 11, 07);
    LocalDate birthday = LocalDate.of(2015, 11, 7);
    System.out.println(Period.between(birthday, currentDate).getYears());

    List<String> list = Arrays.asList("David", "Tu", "Anna", "Lui");
    list = Arrays.asList("d", "c", "b", "a");
    boolean isSorted = list.stream().sorted().collect(Collectors.toList()).equals(list);
    System.out.println(isSorted);


  }
}
