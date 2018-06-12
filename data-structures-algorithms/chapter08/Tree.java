import java.util.Iterator;
import java.util.ArrayList;

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

  /** Returns n space */
  public static String space(int n) {
    String result = "";
    while (n > 0) {
      result += " ";
      n--;
    }
    return result; 
  } 

  /** Prints preorder representation of subtree of T rooted at p having depth d, example call printPreorderIndent(T, T.root(), T) */
  public static <E> void printPreorderIndent(Tree<E> T, Position<E> p, int d) {
    System.out.println(space(2*d) + p.getElement()); // indent based on d
    for (Position<E> c : T.children(p)) {
      printPreorderIndent(T, c, d + 1); // child depth is d + 1
    }
  }

  /** Return total disk space for subtree of T rooted at p */
  public static int diskSpace(Tree<Integer> T, Position<Integer> p) {
    int subtotal = p.getElement();
    for (Position<Integer> c : T.children(p)) {
      subtotal += diskSpace(T, c);
    }
    return subtotal;
  }

  /** Print labeled representation of subtree of T rooted at p having depth d */
  public static <E> void printPreorderLabeled(Tree<E> T, Position<E> p, ArrayList<Integer> path) {
    int d = path.size(); // depth equals the length of the path
    System.out.print(space(2*d)); // print indentation, then label
    for (int j = 0; j < d; j++) {
      System.out.print(path.get(j) + (j == d -1 ? " " : "."));
    }
    System.out.println(p.getElement());
    path.add(1); // add path entry for first child
    for (Position<E> c : T.children(p)) {
      printPreorderLabeled(T, c, path);
      path.set(d, 1 + path.get(d)); // increment last entry of path
    }
    path.remove(d); // restore path to its incoming state
  }

  /** Prints parenthesized representation of subtree of T rooted at p */
  public static <E> void parenthesize(Tree<E> T, Position<E> p) {
    System.out.print(p.getElement());
    if (T.isInternal(p)) {
      boolean firstTime = true;
      for (Position<E> c : T.children(p)) { // determine proper punctuation
        System.out.print(firstTime ? " (" : ", "); // any future passed will get comma
        firstTime = false;
        parenthesize(T, c); // recur on child
      }
      System.out.print(")");
    }
  }
}