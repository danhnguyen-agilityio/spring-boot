public interface Series {
  /** Return next number in series */
  int getNext();

  /** Return an array that contains the next n elements */
  default int[] getNextArray(int n) {
    return getArray(n);
  }

  /** Return an array that contains the next n elements in the series,
   * after skipping elements 
   */
  default int[] skipAndGetNextArray(int skip, int n) {
    getArray(skip);

    return getArray(n);
  }

  /** Return an array containing the next n elements */
  private int[] getArray(int n) {
    int[] vals = new int[n];
    for (int i = 0; i < n; i++) {
      vals[i] = getNext();
    }
    return vals;
  }
}