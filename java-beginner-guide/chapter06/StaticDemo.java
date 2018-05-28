/** Class used to calculate sum two number */
class SumNumber {
  int x;
  int denom = 3;
  static int y;
  static int val = 1024; // a static var

  static double root2;
  static double root3;

  static {
    System.out.println("Inside static block");
    root2 = Math.sqrt(2.0);
    root3 = Math.sqrt(3.0);
  }

  SumNumber(String msg) {
    System.out.println(msg);
  }

  /** Get sum numbers */
  int sum() {
    return x + y;
  }

  /** Divide value with any number */
  static int divide(int num) {
    return val / num;
  }

  /** Can not acces a non static var */
  // static int valDivDenom() {
  //   return val / denom;
  // }
}

/** Static demo */
class StaticDemo {
  public static void main(String args[]) {
    SumNumber ob1 = new SumNumber("Inside constructor");
    ob1.x = 10;
    SumNumber.y = 10;

    System.out.println("Sum: " + ob1.sum());
    System.out.println("1024 / 2 : " + SumNumber.divide(2));
    System.out.println("Root 2: " + SumNumber.root2);
    System.out.println("Root 3: " + SumNumber.root3);
  }
}