package chapter09;

interface A1 {
  default void hello() {
    System.out.println("A");
  }
}

interface B1 extends A1 {}

interface C1 extends A1 {
  void hello();
}

class D1 implements B1, C1 {


  public static void main(String[] args) {
    new D1().hello();
  }

  @Override
  public void hello() {
    System.out.println("D");
  }
}

public class DiamondProblem {
}
