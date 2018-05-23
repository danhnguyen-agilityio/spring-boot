// Upppercase letters
class UpCase {
  public static void main(String args[]) {
    char ch;

    for (int i = 0; i < 10; i++) {
      ch = (char) ('a' + i);
      System.out.println(ch);

      ch = (char) ((int) (ch & 65503)); // turns off the 6th bit
      System.out.println(ch + "  ");
    }
  }
}