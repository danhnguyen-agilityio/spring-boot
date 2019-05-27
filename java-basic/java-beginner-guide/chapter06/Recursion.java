/** Class calculate factorial */
class Factorial {
  int factR(int n) {
    int result;

    if (n == 1) return 1;
    result = factR(n - 1) * n;
    return result;
  }
}

/** Recursion demo */
class Recursion {
  public static void main(String args[]) {
    Factorial f = new Factorial();
    System.out.println("Fac of 4 = " + f.factR(4));
  }
}