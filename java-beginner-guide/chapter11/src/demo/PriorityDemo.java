package demo;

class PriorityDemo implements Runnable {
  int count;
  Thread thread;

  static boolean stop = false;
  static String currentName;

  // Construct a new thread
  PriorityDemo(String name) {
    thread = new Thread(this, name);
    count = 0;
    currentName = name;
  }

  /**
   * Entry point of thread
   */
  public void run() {
    System.out.println(thread.getName() + " starting.");
    do {
      count++;
      if (currentName.compareTo(thread.getName()) != 0) {
        currentName = thread.getName();
        System.out.println("In " + currentName);
      }
    } while (stop == false && count < 10000000);
    stop = true;
    System.out.println(thread.getName() + " terminating");
    // TODO need to impl it.
  }
}

class Main {
  public static void main(String[] args) {
    PriorityDemo mt1 = new PriorityDemo("High Priority");
    PriorityDemo mt2 = new PriorityDemo("Low Priority");
    PriorityDemo mt3 = new PriorityDemo("Normal Priority #1");
    PriorityDemo mt4 = new PriorityDemo("Normal Priority #2");
    PriorityDemo mt5 = new PriorityDemo("Normal Priority #3");
    // FIXME harding code here.
    // set the priorities
    mt1.thread.setPriority(Thread.NORM_PRIORITY + 4);
    mt2.thread.setPriority(Thread.NORM_PRIORITY - 4);

    // start the thread
    mt1.thread.start();
    mt2.thread.start();
    mt3.thread.start();
    mt4.thread.start();
    mt5.thread.start();

    try {
      mt1.thread.join();
      mt2.thread.join();
      mt3.thread.join();
      mt4.thread.join();
      mt5.thread.join();
    } catch (InterruptedException exc) {
      System.out.println("Main thread interrupted");
    }

    System.out.println("High priority thread counted to " + mt1.count);
    System.out.println("Low priority thread counted to " + mt2.count);
    System.out.println("1st priority thread counted to " + mt3.count);
    System.out.println("2nd priority thread counted to " + mt4.count);
    System.out.println("3rd priority thread counted to " + mt5.count);
  }
}

