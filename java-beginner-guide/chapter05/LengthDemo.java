// Use the length array member
class LengthDemo {
  public static void main(String args[]) {
    int table[][] = {
      {1, 2, 3},
      {4, 5},
      {6, 7, 8, 9}
    };

    System.out.println(table.length);

    System.out.println(table[0].length);
    System.out.println(table[1].length);
    System.out.println(table[2].length);
  }
}