/** ArrayList class */
public class ArrayList<E> implements List<E> {
  public static final CAPACITY = 16; // default array capacity
  private E[] data; // generic array used for storage
  private int size = 0; // current number of elements

  /** Constructor list with default capacity */
  public ArrayList() {
    this(CAPACITY); 
  }

  /** Constructs list with given capacity */
  public ArrayList(int capacity) {
    data = (E[]) new Object[capacity]; // safe cast; compiler may give warning
  }

  /** Returns the number of elements in the list */
  int size() {
    return size;
  }

  /** Returns whether the list is empty */
  boolean isEmpty() {
    return size == 0;
  }

  /** Returns the element at index i */
  E get(int i) throws IndexOutOfBoundsException {
    checkIndex(i, size);
    return data[i];
  }

  /** Replaces the element at index i with e, and returns the replaced element */
  E set(int i, E e) throws IndexOutOfBoundsException {
    checkIndex(i, size);
    E temp = data[i];
    data[i] = e;
    return temp;
  }

  /** Inserts element e to be at index i,  shifting all subsequent elements later */
  void add(int i, E e) throws IndexOutOfBoundsException, IllegalStateException {
    checkIndex(i, size);
    if (size == data.length) // not enough capacity
      resize(2 * data.length); // so double the current capacity
    for (int k = size -1; k >= i; k--) { // start by shifting rightmost
      data[k +1] = data[k];
    }
    data[i] = e; // ready to place the new element
    size++;
  }

  /** Removes/ returns the element at index i, shifting subsequent elements earlier */
  E remove(int i) throws IndexOutOfBoundsException {
    checkIndex(i, size);
    E temp = data[i];
    for (int k = i; k < size - 1; k++) { // shift elements to be hole
      data[k] = data[k + 1];
    }
    data[size -1] = null; // help garbage collection
    size--;
    return temp;
  }

  /** Checks whether the given index is in the range [0, n -1] */
  protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
    if (i < 0 || i >= n) {
      throw new IndexOutOfBoundsException("Illegal index: " + i);
    }
  }

  /** Resizes internal array to have given capacity >= size */
  protected void resize(int capacity) {
    E[] temp = (E[]) new Object[capacity];
    for (int k = 0; k < size; k++) {
      temp[k] = data[k];
    }
    data = temp; // start using the new array
  }

}