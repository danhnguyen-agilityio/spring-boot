import java.io.File;

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

  public static void main(String args[]) {
    System.out.println("Factorial of 4: " + factorial(4));

    int[] data = { 1, 3, 4, 5, 8, 12, 23, 34, 56 };
    System.out.println("Binary search: " + binarySearch(data, 12, 0, 8));

    File root = new File("/Users/nguyendanh/training/java-training/data-structures-algorithms");
    System.out.println("Disk usage: " + diskUsage(root));

    int[] data1 = { 1, 2, 3, 4, 5 };
    System.out.println("Sum array: " + linearSum(data1, 5));
  }

}