// 248 252 254
public class ArrayStack<E> implements Stack<E> {
  public static final int CAPACITY = 100; // default array capacity
  private E[] data; // generic array used for storage
  private int t = -1; // index of top element in stack

  /** Constructs stack with default CAPACITY */
  public ArrayStack() {
    this(CAPACITY);
  }
  
  /** Constructs stack with given capacity */
  public ArrayStack(int capacity) {
    data = (E[]) new Object[capacity]; // safe cast; compiler may give warning
  }

  /** Return size */
  public int size() {
    return (t + 1);
  }

  /** Tests whether the stack is empty */
  public boolean isEmpty() {
    return t == -1;
  }

  /** Push element to top of stack */
  public void push(E e) throws IllegalStateException {
    if (size() == data.length) {
      throw new IllegalStateException("Stack is full");
    }
    data[++t] = e; // increment t before storing new item
  }

  /** Returns the top element of stack */
  public E top() {
    if (isEmpty()) return null;
    return data[t];
  }

  /** Returns and removes the top element of stack */
  public E pop() {
    if (isEmpty()) return null;
    E answer = data[t];
    data[t] = null; // dereference to help garbage collection
    t--;
    return answer;
  }
}