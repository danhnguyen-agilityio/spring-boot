/** Class implements a comparator based upon the natural ordering of its element type */
public class DefaultComparator<E> implements Comparator<E> {
  public int compare(E a, E b) {
    return (Comparable<E> a).compareTo(b);
  }
}