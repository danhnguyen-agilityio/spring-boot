/** A collection of objects that are inserted and removed at the front and back of queue */
public interface Deque<E> {

  /** 
   * Returns the number of elements in the deque
   * @return number of elements in the deque
   */
  int size();
  
  /** 
   * Tests wheter the deque is empty
   * @return true if the deque is empty, false otherwise
   */
  boolean isEmpty();

  /**
   * Returns first element of the deque
   * @return first element in the deque (or null if empty)
   */
  E first();

  /**
   * Returns last element of the deque
   * @return last element in the deque (or null if empty)
   */
  E last();

  /** Inserts an element at the front of the deque */
  E addFirst(E e);

  /**
   * Inserts an element at the back of the deque
   */
  E addLast(E e);

  /**
   * Remove and return an element at the first of the deque
   */
  E removeFirst(E e);

  /**
   * Remove and return an element at the last of the deque
   */
  E removeLast(E e);
}