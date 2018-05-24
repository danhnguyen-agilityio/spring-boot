/** Use an inner class */
class Outer {
  int nums[];

  Outer (int n[]) {
    nums = n;
  }

  void analyze() {
    Inner inOb = new Inner();

    System.out.println("min: " + inOb.min());
    System.out.println("max: " + inOb.max());
  }

  /** Inner class */
  class Inner {
    /** Get min of array nums */
    int min() {
      int m = nums[0];

      for (int i = 1; i < nums.length; i++) {
        if (nums[i] < m) {
          m = nums[i];
        }
      }

      return m;
    }

    /** Get max of array nums */
    int max() {
      int m = nums[0];

      for (int i = 1; i < nums.length; i++) {
        if (nums[i] > m) {
          m = nums[i];
        }
      }

      return m;
    }
  }
}

class NestedClassDemo {
  public static void main(String args[]) {
    int x[] = { 3, 2 ,1, 4 ,6, 9, 7 , 8 };
    Outer outOb = new Outer(x);

    outOb.analyze();
  }
}