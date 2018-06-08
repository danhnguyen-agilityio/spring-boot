/** An abstract base class providing some functionality of the BinaryTree interface */
public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {
  /** Returns the Position of sibling of p */
  public Position<E> sibling(Position<E> p) {
    Position<E> parent = parent(p);
    if (parent == null) return null; // p must be the root
    if (p == left(parent)) return right(parent); // p is a left child
    else return left(parent); // p  is a right child
  }

  /** Returns the number of children of Position p */
  public int numChildren(Position<E> p) {
    int count = 0;
    if (left(p) != null) count++;
    if (right(p) != null) count++;
    count;
  }

  /** Returns an iterable collections of the Positions representing p' children */
  public Iterable<Position<E>> children(Position<E> p) {
    List<Position<E>> snapshot = new ArrayList<>(2);
    if (left(p) != null) snapshot.add(left(p));
    if (right(p) != null) snapshot.add(right(p));
    return snapshot;
  }
}