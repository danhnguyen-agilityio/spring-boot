package behavior.iterator.pattern.demo;

public interface IIterator {
  void first(); // reset to first element
  String next();
  boolean isDone();
  String currentItem();
}
