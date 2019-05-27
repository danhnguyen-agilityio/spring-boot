/** 
 * Functional interface for numeric predicates that operate 
 * on an object of type MyIntNum and an integer value
 */
interface MyIntNumPredicate {
  boolean test(MyIntNum mv, int n);
}

/** 
 * This class stores an int value and defines the instance method isFactor()
 * which returns true if its argument is a factor of the stored value
 */
class MyIntNum {
  private int v;

  /** Constructor */
  MyIntNum(int x) {
    v = x;
  }

  /** Get num */
  int getNum() {
    return v;
  }

  /** Return true if n is a factor of v */
  boolean isFactor(int n) {
    return (v % n) == 0;
  }
}

/** Method reference any instance method */
class MethodRefAnyMethod {
  public static void main(String args[]) {
    boolean result;

    MyIntNum myIntNum = new MyIntNum(12);
    MyIntNum myIntNum2 = new MyIntNum(16);

    // myIntNumPredicate refer to the instance method isFactor()
    MyIntNumPredicate myIntNumPredicate = MyIntNum::isFactor;

    result = myIntNumPredicate.test(myIntNum, 3);
    if (result) {
      System.out.println("3 is factor of " + myIntNum.getNum());
    }

    result = myIntNumPredicate.test(myIntNum2, 3);
    if (result) {
      System.out.println("3 is factor of " + myIntNum2.getNum());
    }
  }
}