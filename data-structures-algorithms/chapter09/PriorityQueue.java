/** Interface for the priority queue ADT */
public interface PriorityQueue<K, V> {
  /** Returns the size of queue */
  int size();

  /** Returns boolean indicating whether the queue is empty */
  boolean isEmpty();

  /** Create entry with key and value in queue, returns created entry */
  Entry<K, V> insert(K key, V value) throws IllegalArgumentException;

  /** Returns the entry having min key */
  Entry<K, V> min();

  /** Remvoves and returns the entry having min key */
  Entry<K, V> remove();
}