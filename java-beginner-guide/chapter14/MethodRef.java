/** A functional interface for numeric pridicate that operate on integer value */
interface IntPredicate {
  boolean test(int n);
}

/** This class define two static methods */
class MyIntPredicates {

  /** A static method that returns true if a number is even */
  static boolean isEven(int n) {
    return n% 2 == 0;
  }

  /** A static method that returns true if a number is positive */
  static boolean isPositive(int n) {
    return n > 0;
  }
}

/** Demo method ref */
class MethodRef {

  /** Test number */
  static boolean numTest(IntPredicate p, int v) {
    return p.test(v);
  }

  public static void main(String args[]) {
    boolean result;

    // A method reference to isEven is used
    result = numTest(MyIntPredicates::isEven, 12);
    if (result) {
      System.out.println("12 is even");
    }

    // A method reference to isPositive is passed
    result = numTest(MyIntPredicates::isPositive, 11);
    if (result) {
      System.out.println("11 is positive");
    }
  }
}