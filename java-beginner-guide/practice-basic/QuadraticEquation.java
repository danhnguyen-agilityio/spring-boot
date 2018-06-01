/** 
 * This class calculate quadratic equation
 * @author Danh Nguyen
 * 
 */
class QuadraticEquation {
  /**  */
  calculate(double a,double b,double c) {
    x1 = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
    System.out.println("First solution: " + x1);
    x2 = (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
    System.out.println("First solution: " + x2);
  }
}