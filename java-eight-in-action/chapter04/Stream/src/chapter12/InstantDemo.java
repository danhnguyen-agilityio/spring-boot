package chapter12;

import java.time.Instant;
import java.time.temporal.ChronoField;

public class InstantDemo {
  public static void main(String[] args) {
    Instant instant = Instant.ofEpochSecond(3);
    System.out.println(instant);

    Instant instant1 = Instant.ofEpochSecond(3, 0);
    System.out.println(instant1);

    // One billion nanoseconds (1 seconds)  after 2 seconds
    Instant instant2 = Instant.ofEpochSecond(2, 1_000_000_000);
    System.out.println(instant2);

    // One billion nanoseconds (1 seconds)  before 4 seconds
    Instant instant3 = Instant.ofEpochSecond(4, -1_000_000_000);
    System.out.println(instant3);

//    Instant instant1 = Instant.now();
//    System.out.println(instant1);

//    instant.get(ChronoField.DAY_OF_MONTH); throw exception
  }
}
