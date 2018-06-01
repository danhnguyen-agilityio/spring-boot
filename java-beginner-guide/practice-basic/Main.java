import java.util.Scanner;

/** 
 * This class implement all practice
 * @author Danh Nguyen
 * @version 1.0
 */
public class Main {
  /** 
   * Get intput integer from keyboard
   * @return The value input as int
   */
  static int getNumber() {
    Scanner in = new Scanner(System.in);
    if (in.hasNextInt()) {
      return in.nextInt();
    }
    return -1;
  }

  /** Get info date in month, input value month from keyload */
  static void getInforDateInMonth() {
    int month;
    int year;
    int numberDays;

    while (true) {
      System.out.print("Please enter valid month: ");
      month = getNumber();
      if (month >=1 && month <= 12) {
        break;
      }
    }

    while (true) {
      System.out.print("Please enter valid year: ");
      year = getNumber();
      if (year >= 1) {
        break;
      }
    }

    numberDays = DateInfo.getNumberDayInMonth(month, year);
    System.out.println("Month " + month + " year " + year + " have " + numberDays + " days");
  }

  public static void main(String args[]) {
    getInforDateInMonth();
  }
}