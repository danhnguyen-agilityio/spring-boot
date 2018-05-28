/** Finally demo */
class FinallyDemo {
  public static void main(String args[]) {
    int num[] = { 1, 2, 3, 4 };

    try {
      if (num[6] == 2) {
        return;
      }
    } catch (ArrayIndexOutOfBoundsException exc) {
      System.out.println("No matching");
      return;
    } finally {
      System.out.println("Finally");
    }

    System.out.println("Leaving return");
  }
}