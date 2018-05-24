// 303 305
/** Class used to calculate sum two number */
class SumNumber {
  int x;
  int denom = 3;
  static int y;
  static int val = 1024; // a static var

  /** Get sum numbers */
  int sum() {
    return x + y;
  }

  // Get value after div 2
  static int valDiv2() {
    return val / 2;
  }

  /** Can not acces a non static var */
  // static int valDivDenom() {
  //   return val / denom;
  // }
}

/** Static demo */
class StaticDemo {
  public static void main(String args[]) {
    SumNumber ob1 = new SumNumber();
    ob1.x = 10;
    SumNumber.y = 10;

    System.out.println("Sum: " + ob1.sum());
    System.out.println("1024 / 2 : " + SumNumber.valDiv2());
  }
}