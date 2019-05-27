/** Create own exception */
class NonIntResultException extends Exception {
  int n;
  int d;

  NonIntResultException(int i, int j) {
    n = i;
    d = j;
  }

  /** Get info class exception */
  public String toString() {
    return "Result of " + n + " / " + d + " is non-integer";
  }
}

/** Clas for custom exception demo */
class CustomException {
  public static void main(String args[]) {
    try {
      if (3 % 2 != 0) {
        throw new NonIntResultException(3, 2);
      }
    } catch (NonIntResultException exc) {
      System.out.println(exc);
    }
  }
}