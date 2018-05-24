// Initialize a two dimensional array
class Squares {
  public static void main(String args[]) {
    int riders[][] = new int[7][];
    riders[0] = new int[5];
    riders[1] = new int[5];
    riders[2] = new int[5];
    riders[3] = new int[2];
    riders[4] = new int[2];

    int sqrs[][] = {
      {1,1},
      {2,4},
      {3, 9}
    };
    int i, j;

    for (i = 0; i < 3; i++) {
      for (j = 0; j < 2; j++) {
        System.out.println(sqrs[i][j] + "  ");
      }
      System.out.println();
    }

    int nums1[] = new int[10];
    int nums2[] = new int[10];

    for (i = 0; i < 10; i++) nums1[i] = i;
    for (i = 0; i < 10; i++) nums2[i] = -i;
    nums2 = nums1;
    nums2[3] = 99;
    for (i = 0; i < 10; i++) {
      System.out.println(nums1[i] + "  ");
    }
  }
} 