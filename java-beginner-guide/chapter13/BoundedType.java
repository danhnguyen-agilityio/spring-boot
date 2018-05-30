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

  boolean absEqual(NumericFunction<?> ob) {
    if (Math.abs(num.doubleValue()) == Math.abs(ob.num.doubleValue())) {
      return true;
    }
    return false;
  }
}

/** Bounded types upper */
class BoundedType {
  public static void main(String args[]) {
    NumericFunction<Double> numericFunction = new NumericFunction<Double>(12.55);
    NumericFunction<Integer> iOb = new NumericFunction<Integer>(12);
    NumericFunction<Double> dOb = new NumericFunction<Double>(-12.0);


    System.out.println("Fractional: " + numericFunction.fraction());

    // NumericFunction<String> numericFunction = new NumericFunction<String>("Demo string");

    System.out.println("----------------------------------------------");
    if (iOb.absEqual(dOb)) {
      System.out.println("absolute values are equal");
    } else {
      System.out.println("absolute values are differ");
    }
  }
}