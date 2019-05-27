/** SortedMap ADT */
public interface SortedMap<K,V> extends Map<K,V> {
  /** Returns the entry having the least key */
  Entry<K,V> firstEntry();

  /** Returns the entry having the greatest key */
  Entry<K,V> lastEntry();

  /** Returns the entry with least key greater than or equal to given key */
  Entry<K,V> ceilingEntry(K key);

  /** Returns the entry with greatest key less than or equal to given key */
  Entry<K,V> floorEntry(K key);

  /** Returns the entry with greatest key strictly less than given key */
  Entry<K,V> lowerEntry(K key);

  /** Returns the entry with least key strictly greater than given key */
  Entry<K,V> higherEntry(K key);

  /** Returns the iterable containg all entries with key greater than or equal to fromKey, but strictly less than toKey */
  public Iterable<Entry<K,V>> subMap(K fromKey, K toKey);
}