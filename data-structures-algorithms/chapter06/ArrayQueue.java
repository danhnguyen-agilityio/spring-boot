/** Implementation of the queue ADT using a fixed length array */
public class ArrayQueue<E> implements Queue<E> {
  // instance variable
  private E[] data; // generic array used for storage
  private int f = 0; // index of the front element
  private int sz = 0; // current number of elements

  /** Constructs queue with default capacity */
  public ArrayQueue() {
    this(CAPACITY);
  }

  /** Return size */
  public int size() {
    return sz;
  }

  /** Tests whether the queue is empty */
  public boolean isEmpty() {
    return sz == 0;
  }

  /** Inserts an element at the rear of the queue */
  public void enqueue(E e) throws IllegalStateException {
    if (sz == data.length) throw new IllegalStateException("Queue is full")
    int avail = (t + sz) % data.length; // sue modular arithmetic
    data[avail] = e;
    sz++;
  }

  /** Returns the first element of queue */
  public E first() {
    if (isEmpty()) return null;
    return data[f];
  }

  /** Returns and removes the first element of queue */
  public E dequeue() {
    if (isEmpty()) return null;
    E anwser = data[f];
    data[f] = null; // dereference to help garbage collection
    f = (f + 1) % data.length;
    sz--;
    return anwser;
  }
}