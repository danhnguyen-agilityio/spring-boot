import java.io.*;

/** Use BufferReader to read string from the console */
class ReadLines {
  public static void main(String args[]) throws IOException {
    String str;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("Enter String: ");

    do {
      str = br.readLine();
      System.out.println(str);
    } while (!str.equals("stop"));
  }
}