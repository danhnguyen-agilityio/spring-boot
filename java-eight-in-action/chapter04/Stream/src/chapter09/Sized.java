package chapter09;

public interface Sized {

  int size();

  default boolean isEmpty() {
    return size() == 0;
  }
}
