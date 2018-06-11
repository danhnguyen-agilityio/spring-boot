public class ApplicationTraversal {
  public static void main(String args[]) throws IllegalStateException, IllegalArgumentException {
    LinkedBinaryTree<String> treeList = new LinkedBinaryTree<>();
    Position<String> position = treeList.addRoot("Paper");
    Position<String> left = treeList.addLeft(position, "Title");
    Position<String> right = treeList.addRight(position, "Abstract");
    treeList.addLeft(left, "$1.1");
    treeList.addRight(left, "$1.2");
    treeList.addLeft(right, "$1.1");
    treeList.addRight(right, "$1.2");
    Tree.printPreorderIndent(treeList, treeList.root(), 2);
    Tree.parenthesize(treeList, treeList.root());
  }
}