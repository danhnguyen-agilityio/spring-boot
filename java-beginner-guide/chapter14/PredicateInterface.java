import java.util.function.Predicate;

class PredicateInterface {
  public static void main(String args[]) {
    Predicate<Integer> isEven = (n) -> (n % 2) == 0;

    if (isEven.test(4)) {
      System.out.println("4 is even");
    }
  }
}