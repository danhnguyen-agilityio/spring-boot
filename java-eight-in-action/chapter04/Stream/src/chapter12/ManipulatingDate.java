package chapter12;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class ManipulatingDate {
  public static void main(String[] args) {
    LocalDate date = LocalDate.of(2014, 3, 18);
    LocalDate date1 = date.withYear(2011);
    LocalDate date2 = date1.withDayOfMonth(25);
    LocalDate date3 = date2.with(ChronoField.MONTH_OF_YEAR, 9); // 2011-09-25
    LocalDate date4 = date3.plusWeeks(1); // 2011-10-02
    LocalDate date5 = date4.minusYears(1); // 2010-10-02
    LocalDate date6 = date5.plus(1, ChronoUnit.MONTHS); // 2010-11-02
    System.out.println(date6);

  }
}
