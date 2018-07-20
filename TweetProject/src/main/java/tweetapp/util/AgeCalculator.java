package tweetapp.util;

import java.time.LocalDate;
import java.time.Period;

/**
 * Class used to calculate age
 */
public class AgeCalculator {

  /**
   * Calculate age
   * @param birthDay
   * @param currentDate
   * @return age
   */
  public static int calculateAge(LocalDate birthDay, LocalDate currentDate) {
    if ((birthDay != null) && (currentDate != null)) {
      return Period.between(birthDay, currentDate).getYears();
    } else {
      return 0;
    }
  }
}
