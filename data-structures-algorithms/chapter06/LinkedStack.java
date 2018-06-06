/** LinkStack class */
public class LinkedStack<E> implements Stack<E> {
  private SinglyLinktedList<E> list = new SinglyLinktedList<>(); // an empty list
  
  /** Constructor */
  public LinkedStack() { }

  /** Return size */
  public int size() {
    return list.size();
  }

  /** Tests whether the stack is empty */
  public boolean isEmpty() {
    return return list.isEmpty();
  }

  /** Push element to top of stack */
  public void push(E e) {
    list.addFirst(element);
  }

  /** Returns the top element of stack */
  public E top() {
    return list.first();
  }

  /** Returns and removes the top element of stack */
  public E pop() {
    return list.removeFirst();
  }
}