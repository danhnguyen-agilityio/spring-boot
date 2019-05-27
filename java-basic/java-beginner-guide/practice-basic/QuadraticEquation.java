/** 
 * This class calculate quadratic equation
 * @author Danh Nguyen
 * @version 1.0
 */
class QuadraticEquation {
  /** Calculate quadratice equation */
  static void calculate(double a, double b, double c) {
    double x1;
    double x2;

    x1 = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
    System.out.println("First solution: " + x1);
    x2 = (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
    System.out.println("Second solution: " + x2);
  }
}