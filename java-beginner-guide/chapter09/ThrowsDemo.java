class ThrowsDemo {
  public static char prompt(String str) throws java.io.IOException {
    System.out.println(str + ": ");
    return (char) System.in.read();
  }

  public static void main(String args[]) {
    char ch;

    try {
      ch = prompt("Enter a letter: ");
    } catch (java.io.IOException exc) {
      System.out.println("I/O exception occurred");
    }
  }
}