/** A generic functional interface with two parameters that returns a boolean result */
interface SomeTest<T> {
  boolean test(T n, T m);
}

class GenericFunctoinalInterfaceDemo {
  public static void main(String args[]) {
    // Determines if one integer is a factor of another
    SomeTest<Integer> isFactor = (n ,d) -> (n % d) == 0;

    if (isFactor.test(10, 2)) {
      System.out.println("2 is factor of 10");
    }

    // This lambda expression determines if one string is part of another
    SomeTest<String> isIn = (a, b) -> a.indexOf(b) != -1;

    String str = "Generic Functional Interface";
    if (isIn.test(str, "face")) {
      System.out.println("Face is found");
    }
  }
}