// Class get charactor from keyboad
class KbIn {
  public static void main(String args[]) throws java.io.IOException {
    char ch;
    System.out.println("Press a key: ");
    ch = (char) System.in.read(); // read a character from the keyboard
    System.out.println("Your key is: " + ch);
  } 
}