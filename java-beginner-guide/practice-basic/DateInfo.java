/** 
 * This class check month have how many days
 * @author Danh Nguyen
 * @version 1.0
 */
public class DateInfo {
  /** 
   * This function check that month has how many days
   * @param month The month need check
   * @param year The year need check
   * @return Number days in month
   */
  static int getNumberDayInMonth(int month, int year) {
    switch (month) {
      case 1:
      case 3:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12:
        return 31;
      case 4:
      case 6:
      case 9:
      case 11:
        return 30;
      case 2:
        return year % 4 == 0 ? 29 : 28;
      default:
        return 0;
    }
  }
}