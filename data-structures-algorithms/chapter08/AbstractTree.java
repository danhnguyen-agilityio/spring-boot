/** An abstract base class providing some functionality of the Tree interface */
public abstract class AbstractTree<E> implements Tree<E> {

  /** Returns true if Position p have at least one children element */
  public boolean isInternal(Position<E> p) {
    return numChildren(p) > 0; 
  }

  /** Returns true if Position p have no any children element */
  public boolean isExternal(Position<E> p) {
    return numChildren(p) == 0;
  }

  /** Returns true if Postion p is root of the tree */
  public boolean isRoot(Position<E> p) {
    return p == root();
  }

  /** Returns true if tree does not contain any position */
  public boolean isEmpty() {
    return size() == 0;
  }

  /** Returns the number of levels separating Position p from the root */
  public int depth(Position<E> p) {
    if (isRoot()) return 0;
    else return 1 + depth(parent(p));
  }

  /** Returns the height of the tree */
  private int heightBad() {
    int h = 0;
    for (Postion<E> p : positions()) {
      if (isExternal(p)) { // only consider leaf positions
        h = Math.max(h, depth(p));
      }
    }
    return h;
  }

  /** Returns the height of the subtree rooted at Position p */
  public int height(Position<E> p) {
    int h = 0; // base case if p is external
    for (Position<E> c : children(p)) {
      h = Math.max(h, 1 + height(c))
    }
    return h;
  }

}