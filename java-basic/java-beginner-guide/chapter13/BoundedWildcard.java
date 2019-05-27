/** Class A */
class A {

}

/** Class B */
class B extends A {

}

/** Class C */
class C extends A {

}

/** Class D */
class D {

}

/** Class Gen */
class Gen<T> {
  T ob;

  /** Constructor */
  Gen(T o) {
    ob = o;
  }
}

/** Bounded wildcard demo */
class BoundedWildcard {
  /** Use a bounded wildcard */
  static void test(Gen<? extends A> o) {
    System.out.println("use bounded wildcard");
  }

  /** Determine if the length of two arrays are the same */
  static <T extends Comparable<T>, V extends T> boolean arraysEqualLength(T[] x, V[] y) {
    return x.length == y.length;
  }

  public static void main(String args[]) {
    A a = new A();
    B b = new B();
    C c = new C();
    D d = new D();

    Gen<A> w = new Gen<A>(a);
    Gen<B> w2 = new Gen<B>(b);
    Gen<C> w3 = new Gen<C>(c);
    Gen<D> w4 = new Gen<D>(d);

    test(w);
    test(w2);
    test(w3);

    // Illegal because w4 is not a subclass of A
    // test(w4); 

    System.out.println("--------------------------");
    System.out.println("Demo Generic method");
    Integer nums[] = { 1, 2, 3 };
    Integer nums2[] = { 3, 4, 3 };

    if (arraysEqualLength(nums, nums2)) {
      System.out.println("Length array equal");
    }

    Double dVals = { 1.2, 2.3, 3.5 };
    // This not compile because nums and dVals are not of the same type
    // if (arraysEqualLength(nums, dVals)) {
    //   System.out.println("Length array equal");
    // }
  } 
}