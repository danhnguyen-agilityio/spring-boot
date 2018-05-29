import java.io.*;

/** Console output use PrintWriter */
public class PrintWriterDemo {
  public static void main(String args[]) {
    PrintWriter pw = new PrintWriter(System.out, true);
    int i = 10;
    double d = 123.12;

    pw.println("Using a printwriter");
    pw.println(i);
    pw.println(d);

    pw.println(i + " + " + d + " is " + (i + d));
  }
}