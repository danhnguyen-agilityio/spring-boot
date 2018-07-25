package tweetapp.util;

import java.time.LocalDate;
import java.time.Period;

/**
 * Class used to calculate age
 */
public class AgeCalculator {

  /**
   * Calculate age
   * @param birthday birthday
   * @param currentDate current date
   * @return age
   */
  public static int calculateAge(LocalDate birthday, LocalDate currentDate) {
    if ((birthday != null) && (currentDate != null)) {
      return Period.between(birthday, currentDate).getYears();
    } else {
      return 0;
    }
  }

  /**
   * Calculate max date have given age
   * @param age age
   * @return Date have given age
   */
  public static LocalDate maxDate(int age) {
    return LocalDate.now().minusYears(age);
  }

  /**
   * Calculate min date have given age
   * @param age age
   * @return Date have given age
   */
  public static LocalDate minDate(int age) {
    return LocalDate.now().minusYears(age + 1).plusDays(1);
  }
}
