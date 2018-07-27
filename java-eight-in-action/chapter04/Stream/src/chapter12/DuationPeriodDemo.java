package chapter12;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class DuationPeriodDemo {
  public static void main(String[] args) {
    LocalDateTime dt1 = LocalDateTime.of(2018,07,13, 12, 12, 12);
    LocalDateTime dt2 = LocalDateTime.of(2018,07,12, 11, 11, 13);
    Duration d1 = Duration.between(dt2,dt1);
    System.out.println(d1);

    Period tenDays = Period.between(LocalDate.of(2014, 3, 8), LocalDate.of(2014,3, 18));
    System.out.println(tenDays);

    Duration threeMinutes = Duration.ofMinutes(3);
    Duration threeMinutes2 = Duration.of(3, ChronoUnit.MINUTES);
    System.out.println(threeMinutes2);

    Period tendDays = Period.ofDays(10);
    System.out.println(tendDays);

    Period twoYears = Period.of(2, 6, 1);
    System.out.println(twoYears);
  }
}
