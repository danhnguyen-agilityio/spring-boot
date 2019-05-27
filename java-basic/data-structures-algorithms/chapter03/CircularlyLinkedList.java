/** Circularly linked list */
public class CircularlyLinkedList<E> {
  /** Nested Node class */
  private static class Node<E> {
    private E element; // reference to the element stored at this node
    private Node<E> next; // reference to the subsequent node in the list

    /** Constructor */
    public Node(E e, Node<E> n) {
      element = e;
      next = n;
    }

    /** Get element */
    public E getElement() {
      return element;
    }

    /** Get next node */
    public Node<E> getNext() {
      return next;
    }

    /** Set next node */
    public void setNext(Node<E> n) {
      next = n;
    }
  }

  // instance variables of the Circularly Linked List
  private Node<E> tail = null; // last node of the list (or null if empty)
  private int size = 0; // number of nodes in the list

  /** Constructs an initially empty list */
  public CircularlyLinkedList() { } 

  /** Get size */
  public int size() {
    return size;
  }

  /** Check wether list is empty */
  public boolean isEmpty() {
    return size == 0;
  }

  /** Returns the first element */
  public E first() {
    if (isEmpty()) return null;
    return tail.getNext().getElement(); // the head is after the tail
  }

  /** Returns the last element */
  public E last() {
    if (isEmpty()) return null;
    return tail.getElement();
  }

  /** Rotate the first element to the back of the list */
  public void rotate() {
    if (tail != null) {  // if empty, do nothing
      tail = tail.getNext(); // the old head becomes the new tail
    }
  }

  /** Add element e to the front of the list */
  public void addFirst(E e) {
    if (size == 0) {
      tail = new Node<>(e, null);
      tail.setNext(tail); // link to itself curcularly
    } else {
      Node<E> newest = new Node<>(e, tail.getNext());
      tail.setNext(newest);
    }
    size++;
  }

  /** Add element e to the end of the list */
  public void addLast(E e) {
    addFirst(e); // insert new element ad front of list
    tail = tail.getNext(); // now new element becomes the tail
  }

  /** Remove and returns the first element */
  public E removeFirst() {
    if (isEmpty()) return null; // nothing to remove
    Node<E> head = tail.getNext();
    if (head == tail) tail = null; // must be the only node left
    else tail.setNext(head.getNext()); // removes head from the list
    return head.getElement();
  }
}