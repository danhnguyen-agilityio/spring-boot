import java.util.Iterator;

/** An interface for a tree where nodes can have an arbitrary number of children */
public interface Tree<E> extends Iterable<E> {
  /** Get root of tree */
  Position<E> root();

  /** Get parent position of p  */
  Position<E> parent(Position<E> p) throws IllegalArgumentException;

  /** Get iterable collection containing children position of p */
  Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException;

  /** Get num children of position p */
  int numChildren(Position<E> p) throws IllegalArgumentException;

  /** Return true if Position p is internal */
  boolean isInternal(Position<E> p) throws IllegalArgumentException;

  /** Return true if Position p doese not have any children */
  boolean isExternal(Position<E> p) throws IllegalArgumentException;

  /** Return true of position p is the root of the tree */
  boolean isRoot(Position<E> p) throws IllegalArgumentException;

  /** Return number of positions of tree */
  int size();

  /** Return true is the tree does not contain any positions */
  boolean isEmpty();

  /** Return an iterator for all element of the tree */
  Iterator<E> iterator();

  /** Return an iterable collection of all positions of the tree */
  Iterable<Position<E>> positions();
}