import java.io.*;

/** Use wrapper type */
class AvgNums {
  public static void main(String args[]) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String str;
    int n;
    double sum = 0.0;
    double avg, t;

    System.out.println("Many number: ");
    str = br.readLine();
    n = Integer.parseInt(str);

    System.out.println("enter " + n + " value");
    for (int i = 0; i < n; i++) {
      str = br.readLine();
      t = Double.parseDouble(str); // Convert string to double
      sum += t;
    }

    avg = sum /n;
    System.out.println("average: " + avg);
  }
}