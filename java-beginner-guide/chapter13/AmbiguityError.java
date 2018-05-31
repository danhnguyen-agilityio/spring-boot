class AmbiguityError<T, V> {
  T ob1; 
  V ob2;

  // Compile error because set(V) and set(T) have the same erasure
  /** Set object */
  void set(T o) {
    ob1 = o;
  }

  /** Set object */
  void set(V o) {
    ob2 = o;
  }
}