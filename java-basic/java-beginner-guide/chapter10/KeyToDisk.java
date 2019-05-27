import java.io.*;

/** A simple key to disk utility */
class KeyToDisk {
  public static void main(String args[]) {
    String str;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("enter text: ");

    try (FileWriter fw = new FileWriter("test.txt")) {
      do {
        System.out.println(": ");
        str = br.readLine();

        if (str.compareTo("stop") == 0) {
          break;
        }

        str = str + "\r\n"; // add new line
        fw.write(str);
      } while (str.compareTo("stop") != 0);
    } catch (IOException exc) {
      System.out.println("IO error");
    }
  }
}