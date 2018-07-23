package tweetapp.util;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class AgeCalculatorTest {

  @Test
  public void calculateAge() {
    LocalDate birthDate = LocalDate.of(1993, 11, 07);
    int actual = AgeCalculator.calculateAge(birthDate, LocalDate.of(2018, 07, 22));
    assertEquals(24, actual);
  }

}