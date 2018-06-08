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

  /** Adds positions of subtree rooted at Position p to the given snapshot */
  private void inorderSubtree(Position<E> p, List<Position<E>> snapshot) {
    if (left(p) != null) inorderSubtree(left(p), snapshot);
    snapshot.add(p);
    if (right(p) != null) inorderSubtree(right(p), snapshot);
  }

  /** Returns an iterable collection of positions of tree, reported inorder */
  public Iterable<Position<E>> inorder() {
    List<Position<E>> snapshot = new ArrayList<>();
    if (!isEmpty()) {
      inorderSubtree(root(), snapshot); // fill the snapshot recursively
    }
    return snapshot;
  }

  /** Overrides positions to make inorder the default order for binary trees */
  public Iterable<Position<E> positions() {
    return inorder();
  }
}