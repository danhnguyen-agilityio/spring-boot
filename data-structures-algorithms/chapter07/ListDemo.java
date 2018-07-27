/** List demo */
class ListDemo {
  /** Insertion sort of a positional list of integers into nondecreasing order */
  public static void insertionSort(PositionalList<Integer> list) {
    Position<Integer> marker = list.first(); // last position known to be sorted
    while(market != list.last()) {
      Position<Integer> pivot = list.after(marker);
      int value = pivot.getElement(); // number to be placed
      if (value > marker.getElement()) { // pivot is already sorted
        marker = pivot;
      } else {
        Position<Integer> walk = marker; // find leftmost item greater than value
        while (walk != list.first() && list.before(marker).getElement() > value) {
          walk = list.before(marker);
        }
        list.remove(pivot); // remove pivot entry
        list.addBefore(walk, value); // reinsert value in front of walk
      }
    } 
  }
}
