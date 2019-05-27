
/** Class with two generic types */
class TwoGen<T, V> {
  T ob1;
  V ob2;

  /** Constructor */
  TwoGen(T o1, V o2) {
    ob1 = o1;
    ob2 = o2;
  }

  /** Show types of T and V */
  void showTypes() {
    System.out.println("Type of T: " + ob1.getClass().getName());
    System.out.println("Type of V: " + ob2.getClass().getName());
  }

  /** Get ob1 */
  T getob1() {
    return ob1;
  }

  /** Get ob2 */
  V getob2() {
    return ob2;
  }
}

class GenericDemo {
  public static void main(String args[]) {
    TwoGen<Integer, String> twoGen = new TwoGen<Integer, String>(88, "Generics");

    // Show the types
    twoGen.showTypes();

    int v = twoGen.getob1();
    System.out.println("Value: " + v);

    String str = twoGen.getob2();
    System.out.println("Value: " + str);
  }
} 