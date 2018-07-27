package chapter12;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static java.time.temporal.TemporalAdjusters.*;

public class TemporalAdjustersDemo {

  public static void main(String[] args) {
    TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(temporal -> {
      DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
      int dayToAdd = 1;
      if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
      else if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
      return temporal.plus(dayToAdd, ChronoUnit.DAYS);
    });

    LocalDate date = LocalDate.of(2018, 07, 13)
        .with(nextOrSame(DayOfWeek.SUNDAY))
        .with(TemporalAdjusters.lastDayOfMonth())
        .with(firstInMonth(DayOfWeek.MONDAY)) // 02-07
        .with(previous(DayOfWeek.TUESDAY)) // 26-06
        .with(new NextWorkingDay()) // 27-06
        .with(nextWorkingDay);

    System.out.println(date);
  }
}
