/** A fixed size queue class for characters */
class FixedQueue implements ICharQ {
  private char q[];
  private int putloc, getloc;

  /** COnstruct an empty queue given its size */
  public FixedQueue(int size) {
    q = new char[size];
    putloc = getloc = 0;
  }

  /** Put a character into the queue */
  public void put(char ch) {
    q[putloc++] = ch;
  }

  /** Get a character from the queue */
  public char get() {
    return q[getloc++];
  }
}

/** Demonstrate the ICharQ interface */
class IQDemo {
  public static void main(String args[]) {
    FixedQueue q1 = new FixedQueue(10);
    char ch;

    for (int i = 0; i < 10; i++) {
      q1.put((char) ('A' + i));
    }

    for (int i = 0; i < 10; i++) {
      ch = q1.get();
      System.out.println(ch);
    }
  }
}