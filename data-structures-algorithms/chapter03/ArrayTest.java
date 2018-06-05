import java.util.Arrays;
import java.util.Random;

/** Program showing some array uses */
public class ArrayTest {
  public static void main(String args[]) {
    int data[] = new int[10];
    Random rand = new Random(); // random number generator
    rand.setSeed(System.currentTimeMillis()); // use current time as a seed
    // fill the data array with random numbers from 0 to 99, inclusive
    for (int i = 0; i < data.length; i++) {
      data[i] = rand.nextInt(100); // the next random number
    }
    int[] orig = Arrays.copyOf(data, data.length); // make a copy of the data array
    System.out.println("Arrays equal before sort: " + Arrays.equals(data, orig));
    Arrays.sort(data);
    System.out.println("Arrays equal after sort: " + Arrays.equals(data, orig));
    System.out.println("orig = " + Arrays.toString(orig));
    System.out.println("data = " + Arrays.toString(data));
  } 
}