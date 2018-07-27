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
  Entry<K, V> removeMin();

  /** Sort priority queue */
  // public static <E> void pqSort(PositionalList<E> S, PriorityQueue<E,?> P) {
  //   int  n = S.size();
  //   for (int j = 0; j < n; j++) {
  //     E element = S.remove(S.first());
  //     P.insert(element, null); // element is key, null value
  //   }
  //   for (int j = 0; j < n; j++) {
  //     E element = P.removeMin().getKey();
  //     S.addLast(element); // the smallest key in p is next placed in S
  //   }
  // }
}