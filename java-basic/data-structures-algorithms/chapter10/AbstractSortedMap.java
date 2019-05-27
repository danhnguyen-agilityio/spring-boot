import java.util.Comparator;

/** AbstractSortedMap base class */
public abstract class AbstractSortedMap<K,V> extends AbstractMap<K,V> implements SortedMap<K,V> {
  private Comparator<K>  comp;

  /** Creates an empty sorted map based on the natural ordering of its key */
  public AbstractSortedMap() {
    this(new DefaultComparator<K>());
  }

  /** Crete an empty sorted map using the given comparator */
  public AbstractSortedMap(Comparator<K> c) {
    comp = c;
  }

  /** Method for comparing two entries according to key */
  protected int compare(Entry<K,V> a, Entry<K,V> b) {
    return comp.compare(a.getKey(), b.getKey());
  }

  /** Method for compare two key */
  protected int compare(K a, K b) {
    return comp.compare(a, b);
  }
}