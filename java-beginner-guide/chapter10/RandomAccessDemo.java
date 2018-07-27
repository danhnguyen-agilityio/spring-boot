import java.io.*;

/** Random access file */
class RandomAccessDemo {
  public static void main(String args[]) {
    double data[] = { 19.4, 10.1, 123, 4, 33.0, 88.2 };
    double d;

    // Open and use a random access file
    try (RandomAccessFile raf = new RandomAccessFile("random.dat", "rw")) {
      for (int i = 0; i < data.length; i++) {
        raf.writeDouble(data[i]);
      }

      raf.seek(0); // seek to the first double
      d = raf.readDouble();
      System.out.println("First value is " + d);

      raf.seek(8); // seek to second double
      d = raf.readDouble();
      System.out.println("Second value is " + d);

      raf.seek(8 * 3); // seek to fourth double
      // d = raf.readDouble();
      System.out.println("Fourth value is " + raf.readDouble());

      System.out.println();
    } catch (IOException exc) {
      System.out.println("IO error");
    } 
  }
}