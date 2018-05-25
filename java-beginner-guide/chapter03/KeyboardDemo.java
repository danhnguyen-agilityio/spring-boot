/** Read character from keyoard */
class KeyboardDemo {
  public static void main(String args[]) throws java.io.IOException {
    char ch;

    do {
      System.out.println("Press a key: ");
      ch = (char) System.in.read();
    } while(ch != 'q');
  } 
}