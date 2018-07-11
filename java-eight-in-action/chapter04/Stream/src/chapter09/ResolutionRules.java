package chapter09;

interface A {
  default void hello() {
    System.out.println("A");
  }
}

interface B extends A {
  default void hello() {
    System.out.println("B");
  }
}

class D implements A {
  public void hello() {
    System.out.println("D");
  }
}

public class ResolutionRules extends D implements B, A {
  public static void main(String[] args) {
    new ResolutionRules().hello();
  }
}
