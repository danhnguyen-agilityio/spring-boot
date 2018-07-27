/** Public and private access demo */
class MyClass {
  private int alpha;
  public int beta;
  int gamma;

  /** Set alpha */
  void setAlpha(int a) {
    alpha = a;
  }

  /** Get alpha */
  int getAlpha() {
    return alpha;
  }
}

/** Access modifier demo */
class AccessDemo {
  public static void main(String args[]) {
    MyClass ob = new MyClass();

    ob.setAlpha(-99);
    System.out.println("ob.alpha is " + ob.getAlpha());
  }
}