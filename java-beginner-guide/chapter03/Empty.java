// Empty loop syntax demo
class Empty {
  public static void main(String args[]) {
    int i;

    i = 0;
    for (; i < 10; ) {
      System.out.println("Pass #" + i);
      i++;
    }
  }
}