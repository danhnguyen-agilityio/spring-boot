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

interface E {
  default void hello() {
    System.out.println("A");
  }
}

class F implements A, E {

  @Override
  public void hello() {
    A.super.hello();
  }
}

interface M {
  default String getNumber() {
    return "String";
  }
}

interface N {
  default Integer getNumber() {
    return 42;
  }
}

//class K implements M, N {
//  public static void main(String[] args) {
//    System.out.println(new K().getNumber());
//  }
//}

public class ResolutionRules extends D implements B, A {
  public static void main(String[] args) {
    new ResolutionRules().hello();
  }
}
