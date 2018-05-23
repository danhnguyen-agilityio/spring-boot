// Find the minumun and maximum values in an array
class MinMax {
  public static void main(String args[]) {
    int nums[] = new int[10];
    int min, max;

    nums[0] = 99;
    nums[1] = -10;
    nums[2] = 100;
    nums[3] = 18;
    nums[4] = -985;
    nums[5] = 5623;
    nums[6] = 463;
    nums[7] = 287;
    nums[8] = 48;

    min = max = nums[0];
    for (int i = 1; i < 10; i++) {
      if (nums[i] < min) min = nums[i];
      if (nums[i] > max) max = nums [i];
    }

    System.out.println(min + "   " + max);
  }
}