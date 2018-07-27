/** Doubly linked list */
public class DoublyLinkedList<E> {
  /** Nested Node class */
  private static class Node<E> {
    private E element; // reference to the element stored at this node
    private Node<E> prev; // reference to the previous node in the list
    private Node<E> next; // reference to the subsequent node in the list

    /** Constructor */
    public Node(E e, Node<E> p, Node<E> n) {
      element = e;
      prev = p;
      next = n;
    }

    /** Get element */
    public E getElement() {
      return element;
    }

    /** Get prev node */
    public Node<E> getPrev() {
      return prev;
    }

    /** Set prev node */
    public void setPrev(Node<E> p) {
      prev = p;
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
  private Node<E> header = null; // header setinel
  private Node<E> trailer = null; // trailer sentinel
  private int size = 0; // number of nodes in the 
  
  /** Constructs an initially empty list */
  public SinglyLinkedList() { }

  /** Get size */
  public int size() {
    return size;
  }

  /** Tests whether the linked list is empty */
  public boolean isEmpty() {
    return size == 0;
  }

  /** Returns the first element */
  public E first() {
    if (isEmpty()) return null;
    return header.getNext().getElement();
  }

  /** Returns the last element */
  public E last() {
    if (isEmpty()) return null;
    return trailer.getPrev().getElement();
  }

  /** Adds element e to the linked list in between the given nodes */
  private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
    // Create and link a new node
    Node<E> newest = new Node<>(e, predecessor, successor);
    predecessor.setNext(newest);
    successor.setPrev(newest);
    size++;
  }

  /** Add element e to the front of the list */
  public void addFirst(E e) {
    addBetween(e, header, header.getNext()); // place just after the header
  }

  /** Add element e to the end of the list */
  public void addLast(E e) {
    addBetween(e, trailer.getPrev(), trailer); // place just before the trailer
  }

  /** Removes the given node from the list and returns its element */
  private E remove(Node<E> node) {
    Node<E> predecessor = node.getPrev();
    Node<E> successor = node.getNext();
    predecessor.setNext(successor);
    successor.setPrev(predecessor);
    size--;
    return node.getElement();
  }

  /** Remove and returns the first element */
  public E removeFirst() {
    if (isEmpty()) return null;
    return remove(header.getNext());
  }

  /** Remove and returns the last element */
  public E removeLast() {
    if (isEmpty()) return null;
    return remove(trailer.getPrev());
  }
}