/** An interface for a binary tree, in which each node has at most two children */
public interface BinaryTree<E> extends Tree<E> {
  /** Returns the Position of left child of p (or null if no child exist) */
  Position<E> left(Position<E> p) throws IllegalArgumentException;

  /** Returns the Position of right child of p (or null if ) */
  Position<E> right(Position<E> p) throws IllegalArgumentException;

  /** Returns the Position ofsibling of p (or null if no sibling exist) */
  Position<E> sibling(Position<E> p) throws IllegalArgumentException;
}