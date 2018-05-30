/** Numeric function */
class NumericFunction<T extends Number> {
  T num;

  /** Constructor */
  NumericFunction(T n) {
    num = n;
  }

  /** Return the fraction component */
  double fraction() {
    return num.doubleValue() - num.intValue();
  }
}

/** Bounded types upper */
class BoundedType {
  public static void main(String args[]) {
    NumericFunction<Double> numericFunction = new NumericFunction<Double>(12.55);

    System.out.println("Fractional: " + numericFunction.fraction());

    // NumericFunction<String> numericFunction = new NumericFunction<String>("Demo string");
  }
}