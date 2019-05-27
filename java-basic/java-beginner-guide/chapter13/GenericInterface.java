/** Generic containment interface */
interface Containment<T> {
  /** Tests if a specific item is contained within an object that implements Containment */
  boolean contains(T o);
}

/** Implement containment */
class MyClass<T> implements Containment<T> {
  T[] arrayRef;

  MyClass(T[] o) {
    arrayRef = o;
  }

  public boolean contains(T o) {
    for (T x : arrayRef) {
      if (x.equals(o)) {
        return true;
      }
    }
    return false;
  }
}

/** GenericInterface demo */
class GenericInterface {
  public static void main(String args[]) {
    Integer x[] = { 1, 2, 3 };

    MyClass<Integer> myClass = new MyClass<Integer>(x);

    if (myClass.contains(2)) {
      System.out.println("2 is in ob");
    }

    // illegal because myClass is an Integer and 2.6 is a Double value
    // if (myClass.contains(2.6)) {
      // System.out.println("2.6 is in ob");
    // }
  }
}

