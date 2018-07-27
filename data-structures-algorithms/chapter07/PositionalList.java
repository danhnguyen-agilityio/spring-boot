import java.util.Iterator;

/**
 * This interface define behavior of list and get element by position, not use index 
 * Not use index, because index can change when user insert or remove element
 */
public interface PositionalList<E> extends Iterable<E> {
  /**  Returns the number of elements in the list */
  int size();

  /** Tests whether the list is empty */
  boolean isEmpty();

  /** Returns the first position in the list (or null if empty) */
  Position<E> first();

  /** Returns the last position in the list (or null if empty) */
  Position<E> last();

  /** Returns the position immediately before Position p (or null, if p is first) */
  Position<E> before(Position<E> p) throws IllegalArgumentException;

  /** Returns the position immediately after Position p (or null, if p is first) */
  Position<E> after(Position<E> p) throws IllegalArgumentException;

  /** Inserts element e at the front of the list and returns its new Position */
  Position<E> addFirst(E e);

  /** Inserts element e at the back of the list and returns its new Position */
  Position<E> addLast(E e);

  /** Inserts element e immediately before Position p and returns its new Position */
  Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException;

  /** Inserts element e immediately after Position p and returns its new Position */
  Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException;

  /** Replaces the element stored at Position p and returns the replaced element */
  E set(Position<E> p, E e) throws IllegalArgumentException;

  /** Removes the element stored at Position p and returns it */
  E remove(Position<E> p) throws IllegalArgumentException;
}