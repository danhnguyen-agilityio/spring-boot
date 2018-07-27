package chapter12;

import java.time.*;
import java.time.temporal.ChronoField;

public class LocalDateDemo {
  public static void main(String[] args) {
    System.out.println("Local date demo----------------");
    LocalDate  date = LocalDate.of(2018, 07, 13);
    int day = date.getDayOfMonth();
    System.out.println(day);

    DayOfWeek dow = date.getDayOfWeek();
    System.out.println(dow);

    int len = date.lengthOfMonth();
    System.out.println(len);

    boolean leap = date.isLeapYear();
    System.out.println(leap);

    int year = date.get(ChronoField.YEAR);
    System.out.println(year);

    int month = date.get(ChronoField.MONTH_OF_YEAR);
    System.out.println(month);

    System.out.println("Local time demo--------------");
    LocalTime time = LocalTime.of(13, 45, 20);
    int hour = time.getHour();
    System.out.println(hour);

    int minute = time.getMinute();
    System.out.println(minute);

    int second = time.getSecond();
    System.out.println(second);

    System.out.println("Parse demo----------------");
    LocalDate date1 = LocalDate.parse("2018-07-13");
    System.out.println(date1);

    LocalTime time1 = LocalTime.parse("13:45:20");
    System.out.println(time1);

    System.out.println("LocalDateTime demo---------");
    LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18,13,45,20);
    System.out.println(dt1);

    LocalDateTime dt2 = LocalDateTime.of(date, time);
    System.out.println(dt2);

    LocalDateTime dt3 = date.atTime(13, 45, 20);
    System.out.println(dt3);

    LocalDateTime dt4 = date.atTime(time);
    System.out.println(dt4);

    LocalDateTime dt5 = time.atDate(date);
    System.out.println(dt5);

    LocalDate date2 = dt1.toLocalDate();
    LocalTime time2 = dt1.toLocalTime();
  }
}
