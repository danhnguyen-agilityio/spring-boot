/** Singly linked list */
public class SinglyLinkedList<E> {
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

  // instance variables of the Singly Linked List
  private Node<E> head = null; // head node of the list (or null if empty)
  private Node<E> tail = null; // last node of the list (or null if empty)
  private int size = 0; // number of nodes in the list

  /** Constructs an initially empty list */
  public SinglyLinkedList() { }

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
    return head.getElement();
  }

  /** Returns the last element */
  public E last() {
    if (isEmpty()) return null;
    return tail.getElement();
  }

  /** Add element e to the front of the list */
  public void addFirst(E e) {
    head = new Node<>(e, head); // create and link a new node
    if (size == 0) {
      tail = head; // new node becomes tail
    }
    size++;
  }

  /** Add element e to the end of the list */
  public void addLast(E e) {
    Node<E> newest = new Node<>(e, null); // node will eventually by the tail
    if (size == 0) {
      head = newest; // previously empty list
    } else {
      tail.setNext(newest); // new node after existing tail
    }
    tail = newest; // new node becomes the tail
    size++;
  }

  /** Remove and returns the first element */
  public E removeFirst() {
    if (isEmpty()) return null; // nothing to remove
    E answer = head.getElement();
    head = head.getNext(); // will become null if list had only one node
    size--;
    if (size == 0) {
      tail = null; // list is now empty
    }
    return answer;
  }
}