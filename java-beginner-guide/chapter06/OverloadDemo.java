/** Demonstrate method overloading */
class OverLoad {
  void ovlDemo() {
    System.out.println("no param");
  }

  /** Overload ovlDemo for one params */
  void ovlDemo(int a) {
    System.out.println("one param");
  }

  /** Overload ovlDemo for two int params */
  int ovlDemo(int a, int b) {
    System.out.println("two param");
    return a + b;
  }

  /** Overload ovlDemo for differ return type */
  // void ovlDemo(int a, int b) {
  //   System.out.println("two int param return boolean");
  // }

  /** Overload ovlDemo for two one params */
  double ovlDemo(double a, double b) {
    System.out.println("two double param");
    return a + b;
  }
}

/** Overload demo */
class OverloadDemo {
  public static void main(String args[]) {
    OverLoad ob = new OverLoad();
    int resI;
    double resD;

    ob.ovlDemo();
    ob.ovlDemo(1);
    ob.ovlDemo(1,2);
    ob.ovlDemo(1.3, 2.4);
  }
}