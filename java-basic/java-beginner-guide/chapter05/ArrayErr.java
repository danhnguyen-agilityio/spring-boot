/** Demonstrate an array overrun */
class ArrayErr {
  public static void main(String args[]) {
    int sample[] = new int[10];
    int i;

    for (i = 0; i < 1000; i = i + 1) {
      sample[i] = i;
    }
  }
}