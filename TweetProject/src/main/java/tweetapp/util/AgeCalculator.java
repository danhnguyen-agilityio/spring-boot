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
}
