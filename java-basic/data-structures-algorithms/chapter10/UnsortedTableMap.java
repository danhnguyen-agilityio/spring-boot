import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Iterator;

/** An implementation of a map use ArrayList as an unsorted table */
public class UnsortedTableMap<K,V> extends AbstractMap<K,V> {
  /** Underlying storage for the map of entries */
  private ArrayList<MapEntry<K,V>> table = new ArrayList<>();

  /** Constructs an initially empty map */
  public UnsortedTableMap() {}

  /** Returns the index of an entry with equal key, or -1 if not found */
  private int findIndex(K key) {
    int n = table.size();
    for (int j = 0; j < n; j++) {
      if (table.get(j).getKey().equals(key)) {
        return j;
      }
    }
    return -1; // special value denotes that key was not found
  }

  /** Returns the number of entries in the map */
  public int size() {
    return table.size();
  }

  /** Returns the value associated with the specified key (or else null) */
  public V get(K key) {
    int j = findIndex(key);
    if (j == -1) return null; // not found
    return table.get(j).getValue();
  }

  /** Associates given value with given key, replacing a previous value (if any) */
  public V put(K key, V value) {
    int j = findIndex(key);
    if (j == -1) {
      table.add(new MapEntry<>(key, value)); // add new entry
      return null;
    } else { // key already exists
      return table.get(j).setValue(value); // replaced value is returned
    }
  }

  /** Removes the entry with the specified key (if any), and returns its value */
  public V remove(K key) {
    int j = findIndex(key);
    int n = size();
    if (j == -1) return null;
    V answer = table.get(j).getValue(); 
    if (j != n -1) {
      table.set(j, table.get(n -1)); // relocate last entry to entry need remove 
    }
    table.remove(n - 1); // remove the last entry
    return answer;
  }

  /** Class EntryIterator supports for creating class EntryIterable */
  private class EntryIterator implements Iterator<Entry<K,V>> {
    private int j = 0;

    /** Returns boolean indicating whether Map has next value */
    public boolean hasNext() {
      return j < table.size();
    }

    /** Returns next entry */
    public Entry<K,V> next() {
      if (j == table.size()) throw new NoSuchElementException();
      return table.get(j++);
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  /** Class EntryIterable supports for public entrySet method */
  private class EntryIterable implements Iterable<Entry<K,V>> {
    public Iterator<Entry<K,V>> iterator() {
      return new EntryIterator();
    }
  }

  /** Returns an iterable collection of all entries of the map */
  public Iterable<Entry<K,V>> entrySet() {
    return new EntryIterable();
  }
}