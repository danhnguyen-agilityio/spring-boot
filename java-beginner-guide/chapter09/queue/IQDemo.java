/** A fixed size queue class for characters */
class FixedQueue implements ICharQ {
  private char q[];
  private int putloc, getloc;

  /** Construct an empty queue given its size */
  public FixedQueue(int size) {
    q = new char[size];
    putloc = getloc = 0;
  }

  /** Put a character into the queue */
  public void put(char ch) throws QueueFullException {
    if (putloc == q.length) {
      throw new QueueFullException(q.length);
    }

    q[putloc++] = ch;
  }

  /** Get a character from the queue */
  public char get() throws QueueEmptyException {
    if (getloc == putloc) {
      throw new QueueEmptyException();
    }

    return q[getloc++];
  }
}

/** Demonstrate the ICharQ interface */
class IQDemo {
  public static void main(String args[]) {
    FixedQueue q1 = new FixedQueue(10);
    char ch;

    try {
      for (int i = 0; i < 11; i++) {
        q1.put((char) ('A' + i));
      }
      System.out.println();
    } catch (QueueFullException exc) {
      System.out.println(exc);
    }

    try {
      for (int i = 0; i < 11; i++) {
        ch = q1.get();
        System.out.println(ch);
      }
    } catch (QueueEmptyException exc) {
      System.out.println(exc);
    }
  }
}
