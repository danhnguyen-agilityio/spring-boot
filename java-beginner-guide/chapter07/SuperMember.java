/** Use super to overcome name hiding */
class A {
  int i;
}

/** Create a subclass by extending class A */
class B extends A {
  int i;

  /** Constructor with 2 params */
  B(int a, int b) {
    super.i = a;
    i = b;
  }

  /** Show info value in superclass and subclass */
  void show() {
    System.out.println("superclass: " + super.i);
    System.out.println("subclass: " + i);
  }
}

/** Class SuperMember demonstate use super */
class SuperMember {
  public static void main(String args[]) {
    B subOb = new B(1, 2);
    
    subOb.show();
  }
}