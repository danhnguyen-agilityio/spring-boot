import java.io.IOException;

/** Read an array of bytes from the keyboard */
class ReadBytes {
  public static void main(String args[]) throws IOException {
    byte data[] = new byte[10];

    System.out.println("enter: ");
    System.in.read(data); // Read an array of bytes from keyboard
    System.out.println("You entered");
    for (int i = 0; i < data.length; i++) {
      System.out.println((char) (data[i]));
    }
  }
}