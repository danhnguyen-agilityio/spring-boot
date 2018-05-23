// A queue class for characters
class Queue {
  char q[];
  int putloc, getloc;

  Queue(int size) {
    q = new char[size];
    putloc = getloc = 0;
  }

  // put a character into the queue
  void put(char ch) {
    if (putloc == q.length) {
      System.out.println(" - Queue is full");
      return;
    }
    q[putloc++] = ch;
  }

  // get a character from the queue
  char get() {
    if (getloc == putloc) {
      System.out.println(" - Queue is empty");
      return (char) 0;
    }

    return q[getloc++];
  }
}

class QDemo {
  public static void main(String args[]) {
    Queue bigQ = new Queue(100);
    Queue smallQ = new Queue(4);
    char ch;
    int i;

    System.out.println("Using bigQ to store the alphabet");
    for (i=0; i < 26; i++) {
      bigQ.put((char) ('A' + i));
    }

    System.out.println("Content of bigQ: ");
    for (i = 0; i < 26; i++) {
      ch = bigQ.get();
      if (ch != (char) 0) System.out.println(ch);
    }

    System.out.println("\n");
  }
}