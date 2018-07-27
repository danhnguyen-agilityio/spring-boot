import java.io.File;
import java.util.Arrays;

/** Recursion demo */
public class RecursionDemo {
  /** Factorial number */
  public static int factorial(int n) throws IllegalArgumentException {
    if (n < 0) throw new IllegalArgumentException();
    else if (n == 0) return 1;
    else return n * factorial(n - 1);
  }

  /**
   * Returns true if the target value is found in the indicated portion of the data array
   * This search only considers the array portion from data[low] to data[high] inclusive
   */
  public static boolean binarySearch(int[] data, int target, int low, int high) {
    if (low > high) return false;
    int mid = (low + high) / 2;
    if (target == data[mid]) return true; // found a match
    else if (target < data[mid]) return binarySearch(data, target, low, mid - 1); // recur left of the middle
    else return binarySearch(data, target, mid + 1, high); // recur right of the middle
  }

  /** 
   * Calculateds the total disk usage (int byte) of the portion of the file system rooted
   * at the given path, given printing a summary akin to the standard 'du' Unix tool
   */
  public static long diskUsage(File root) {
    long total = root.length(); // start with direct disk usage
    if (root.isDirectory()) { // and if this is a directory
      for (String childname : root.list()) { // then for each child
        File child = new File(root, childname); // compose full path to child
        total += diskUsage(child); // add child' usage to total
      }
    }
    System.out.println(total + "\t" + root); // descriptive output
    return total; // return the grand total
  }

  /** Summing an array of integers using linear recursion */
  public static int linearSum(int[] data, int n) {
    if (n == 0) return 0;
    else return linearSum(data, n - 1) + data[n - 1];
  }

  /** Reverses the contents of subarray data[low] through data[high] inclusive */
  public static void reverseArray(int[] data, int low, int high) {
    if (low < high) { // if at least two elements in subarray
      int temp = data[low]; // swap data[low] and data[high]
      data[low] = data[high];
      data[high] = temp; 
      reverseArray(data, low + 1, high - 1); // recur on the rest
    }
  }

  /** Power fucntion */
  public static double power(double x, int n) {
    if (n == 0) return 1;
    else {
      double partial = power(x, n/2);
      double result = partial * partial;
      if (n % 2 == 1) result *= x;
      return result;
    }
  }

  /** Returns the sum of subarray data[low] through data[high] inclusive */
  public static int binarySum(int[] data, int low, int high) {
    if (low > high) return 0; // zero elements in subarray
    else if (low == high) return data[low]; // one element in subarray
    else {
      int mid = (low + high) / 2;
      return binarySum(data, low, mid) + binarySum(data, mid + 1, high);
    }
  }

  /** Returns true if there are no duplicate values from data[low] through data[high] */
  public static boolean unique(int[] data, int low, int high) {
    if (low >= high) return true; // at most one item
    else if (!unique(data, low, high -1)) return false; // duplicate in first n - 1
    else if (!unique(data, low + 1, high)) return false; // duplicate in last n - 1
    else return data[low] != data[high]; // do first and last differ???
  }

  /** Returns the nth Fibonacci number (inefficiently) */
  public static long fibonacciBad(int n) {
    if (n <= 1) return n;
    else return fibonacciBad(n -2) + fibonacciBad(n - 1);
  }

  /** Returns array containg the pair of Fibonacci numbers, F(n) and F(n-1) */
  public static long[] fibonacciGood(int n) {
    if (n <= 1) {
      long[] answer = {n, 0};
      return answer;
    } else {
      long[] temp = fibonacciGood(n - 1);  // returns [F(n-1), F(n-2)]
      long[] answer = {temp[0] + temp[1], temp[0]};
      return answer;
    }
  }

  /** Returns true if the target value is found in the data array */
  public static boolean binarySearchIterative(int[] data, int target) {
    int low = 0;
    int high = data.length - 1;
    while (low <= high) {
      int mid = (low + high) / 2;
      if (target == data[mid]) return true; // found a match
      else if (target < data[mid]) high = mid - 1; // only consider values left of mid
      else low = mid + 1; // only consider values right of mid
    }
    return false; // loop ended without success
  }

  public static void reverseIterative(int[] data) {
    int low = 0;
    high = data.length;
    while (low < high) { // swap data[low] and data[high]
      int temp = data[low];
      data[low++] = data[high]; // post increment of low
      data[high--] = temp; // post decrement of high 
    }
  }

  public static void main(String args[]) {
    System.out.println("Factorial of 4: " + factorial(4));

    int[] data = { 1, 3, 4, 5, 8, 12, 23, 34, 56 };
    System.out.println("Binary search: " + binarySearch(data, 12, 0, 8));

    File root = new File("/Users/nguyendanh/training/java-training/data-structures-algorithms");
    System.out.println("Disk usage: " + diskUsage(root));

    int[] data1 = { 1, 2, 3, 4, 5 };
    System.out.println("Sum array: " + linearSum(data1, 5));

    reverseArray(data1, 0, 4);
    System.out.println("Reverse array: " + Arrays.toString(data1));

    System.out.println("Power(2,13): " + power(2, 13));

    System.out.println("Sum: " + binarySum(data1, 0, 4));

    System.out.println("Unique: " + unique(data1, 0, 4));

    System.out.println("Recursive: " + fibonacciGood(5)[0]);
  }

}