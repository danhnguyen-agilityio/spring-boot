import java.util.Scanner;

/** 
 * This class implement all practice
 * @author Danh Nguyen
 * @version 1.0
 */
public class Main {
  static Scanner in;

  /** 
   * Get intput integer from keyboard
   * @return The value input as int
   */
  static int getNumberInt() {
    int value;

    while (true) {
      in = new Scanner(System.in);
      if (in.hasNextInt()) {
        return in.nextInt();
      }
      System.out.println("Please enter only integer number: ");
    }
  }

  /** 
   * Get input double from keyboard 
   * @return The value input as double
   */
  static double getNumberDouble() {
    double value;

    while (true) {
      in = new Scanner(System.in);
      if (in.hasNextDouble()) {
        return in.nextDouble();
      }
      System.out.println("Please enter only double number: ");
    }
  }

  /** 
   * Get input string from keyboard 
   * @return The value input as string
   */
  static String getString() {
    in = new Scanner(System.in);
    return in.nextLine();
  }

  /** Get info date in month, input value month from keyboard */
  static void getInforDateInMonth() {
    int month;
    int year;
    int numberDays;

    while (true) {
      System.out.print("Please enter valid month: ");
      month = getNumberInt();
      if (month >=1 && month <= 12) {
        break;
      }
    }

    while (true) {
      System.out.print("Please enter valid year: ");
      year = getNumberInt();
      if (year >= 1) {
        break;
      }
    }

    numberDays = DateInfo.getNumberDayInMonth(month, year);
    System.out.println("Month " + month + " year " + year + " have " + numberDays + " days");
  }

  /** Quadratic equation */
  static void quadraticEquation() {
    double a;
    double b;
    double c;

    System.out.print("Please enter a: ");
    a = getNumberDouble();

    System.out.print("Please enter b: ");
    b = getNumberDouble();

    System.out.print("Please enter c: ");
    c = getNumberDouble();

    QuadraticEquation.calculate(a, b, c);
  }

  /** Implement getting next day */
  static void nextDay() {
    String day;

    while (true) {
      try {
        System.out.print("Please enter valid day: ");
        day = getString().toUpperCase();
        System.out.println(DayInWeek.getNextDay(DayInWeek.valueOf(day)));
        return;
      } catch (IllegalArgumentException e) {
        System.out.println("Day entered invalid.");
      }
    }
  }

  /** Choose practice that you want to demo */
  static void choosePractice() {
    int practice;

    while (true) {
      System.out.print("Please choose practice that you want to demo (1 -> 5): ");
      practice = getNumberInt();
      if (practice >= 1 && practice <= 5) {
        break;
      }
    }

    switch (practice) {
      case 1:
        System.out.println("Check month have how many days: ");
        getInforDateInMonth();
        break;  
      case 2:
        System.out.println("Quadratic equation: ");
        quadraticEquation();
        break;
      case 3:
        System.out.println("Return next day: ");
        nextDay();
        break;
      case 4:
        System.out.println("String Function Demo");
        StringFunction.test();
      case 5:
    }
  }

  public static void main(String args[]) {
    choosePractice();
  }
}