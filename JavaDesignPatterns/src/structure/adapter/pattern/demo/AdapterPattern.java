package structure.adapter.pattern.demo;

public class AdapterPattern {
  public static void main(String[] args) {
    CalculatorAdapter cal = new CalculatorAdapter();
    Triangle t = new Triangle(20, 10);
    System.out.println(cal.getArea(t));
  }
}
