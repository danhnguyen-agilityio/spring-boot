/** Realization of a FIFO queue sa an adaptation of a SinglyLinkedList */
public class LinkedQueue<E> implements Queue<E> {
  private SinglyLinkedList<E> list = new SinglyLinkedList<>(); // an empty list

  /** Constructor default */
  public LinkedQueue() {}

  /** Return size */
  public int size() {
    return list.size();
  }

  /** Tests whether the queue is empty */
  public boolean isEmpty() {
    return list.isEmpty();
  }

  /** Inserts an element at the rear of the queue */
  public void enqueue(E e) {
    list.addLast(element);
  }

  /** Returns the first element of queue */
  public E first() {
    list.first();
  }

  /** Returns and removes the first element of queue */
  public E dequeue() {
    return list.removeFirst();
  }
}