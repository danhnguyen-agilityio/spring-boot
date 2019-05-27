/** Functional interface */
interface MyValue {
  double getValue();
}

/** Functional interface */
interface MyParamValue {
  double getValue(double v);
}

/** LambdaDemo class */
class LambdaDemo {
  public static void main(String args[]) {
    MyValue myValue = () -> 98.6;

    MyParamValue myParamValue = (n) -> 1.0 / n;

    System.out.println("My value: " + myValue.getValue());
    System.out.println("My value: " + myParamValue.getValue(2));

    // myValue = () -> "three"; // Error! String not compatible with double
    // myParamValue = () -> Math.random(); // Error! Parameter required
  }
}