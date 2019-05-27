/** A functional interface for numeric pridicate that operate on integer value */
interface IntPredicate {
  boolean test(int n);
}

/** This class define two static methods */
class MyIntPredicates {
  private int v;

  /** Constructor */
  MyIntPredicates(int x) {
    v = x;
  }

  /** A static method that returns true if a number is even */
  static boolean isEven(int n) {
    return n% 2 == 0;
  }

  /** A static method that returns true if a number is positive */
  static boolean isPositive(int n) {
    return n > 0;
  }

  /** Return true if n is a factor of v */
  boolean isFactor(int n) {
    return (v % n) == 0;
  }

  /** Get number */
  int getNum() {
    return v;
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

    System.out.println("-------------------------------");
    System.out.println("Method references to Instance methods");
    MyIntPredicates myIntPredicates = new MyIntPredicates(12);
    MyIntPredicates myIntPredicates2 = new MyIntPredicates(16);

    // A method reference to isFactor on myIntPredicates is created
    IntPredicate ip = myIntPredicates::isFactor;
    result = ip.test(3);
    if (result) {
      System.out.println("3 is factor of " + myIntPredicates.getNum());
    }

    // A method reference to isFactor on myIntPredicates2 is created
    ip = myIntPredicates2::isFactor;
    result = ip.test(3);
    if (result) {
      System.out.println("3 is factor of " + myIntPredicates2.getNum());
    }

  }
}