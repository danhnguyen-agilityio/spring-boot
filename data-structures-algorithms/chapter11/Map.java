/** Interface Map */
public interface Map<K,V> {

  /** Returns the number of entries in M */
  int size();

  /** Returns a boolean indicating whether M is empty */
  boolean isEmpty();

  /** Returns the value v associated with key, returns null if no entry exists */
  V get(K key);

  /** Return old value and replace value if entryexists, otherwise return null and add entry (key, value) to Map */
  V put(K key, V value);

  /** Returns from M the entry with key equal to k, and returns its value; if M has no such entry, then returns null */
  V remove(K k);

  /** Returns an iterable collection containing all the keys stored in M */
  Iterable<K> keySet();

  /**
   * Returns an iterable collection containing all the values of entries stored in M 
   * (with repetition if multiple keys map to the same value)
   */
  Iterable<V> values();

  /** Returns an iterable collection containing all the entries in Map */
  Iterable<Entry<K,V>> entrySet();
}