/** Demonstate the shift << and >> operators */
class ShiftDemo {
  public static void main(String args[]) {
    int val = 1;

    for (int i = 0; i < 8; i++) {
      val <<= 1;
      System.out.println("Value: " + val);
    }

    val = 128;
    for (int i = 0; i < 8; i++) {
      val >>= 1;
      System.out.println("Value: " + val);
    }
  }
}