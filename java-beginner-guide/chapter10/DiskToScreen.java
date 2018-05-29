import java.io.*;

class DiskToScreen {
  public static void main(String args[]) {
    String s;

    // Create and use a FileReader wrapped int a BufferedReader
    try (BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
      while ((s = br.readLine()) != null) {
        System.out.println(s);
      }
    } catch (IOException exc) {
      System.out.println("IO error");
    }
  }
}