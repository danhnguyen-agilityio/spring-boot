/** Summation class to get sum */
class Summation {
  private int sum;

  /** Constructor use generic */
  <T extends Number> Summation(T arg) {
    sum = 0;

    for (int i = 0; i <= arg.intValue(); i++) {
      sum += i;
    }
  }

  /** Get sum */
  int getSum() {
    return sum;
  }
}

/** Generic constructor demo */
class GenericConstructor {
  public static void main(String args[]) {
    Summation summation = new Summation(4.0);

    System.out.println(summation.getSum());
  }
}