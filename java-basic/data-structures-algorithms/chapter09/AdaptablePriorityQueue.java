/** Interface for the adaptable priority queue ADT */
public interface AdaptablePriorityQueue<K,V> {

  /** Remove the given entry */
  void remove(Entry<K,V> entry) throws IllegalArgumentException;

  /** Replace the key of an entry */
  void replaceKey(Entry<K,V> entry, K key) throws IllegalArgumentException;

  /** Replace the value of an entry */
  void replaceValue(Entry<K,V> entry, V value) throws IllegalArgumentException;
}