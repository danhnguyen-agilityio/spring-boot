package chapter12;

import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.JapaneseDate;
import java.util.Locale;

public class CalenderSystem {
  public static void main(String[] args) {
    LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
    JapaneseDate japaneseDate = JapaneseDate.from(date);
    System.out.println(japaneseDate);

    Chronology japaneseChronology = Chronology.ofLocale(Locale.JAPAN);
    ChronoLocalDate now = japaneseChronology.dateNow();
    System.out.println(now);
  }
}
