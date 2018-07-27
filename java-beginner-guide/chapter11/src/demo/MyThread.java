package demo;

/**
 * Create thread by implementing Runnable
 */
class MyThread implements Runnable {
  String name;

  public MyThread(String name) {
    this.name = name;
  }

  /**
   * Entry point of thread
   */
  public void run() {
    System.out.println(name + " starting.");
    try {
      for (int i = 0; i < 10; i++) {
        Thread.sleep(400);
        System.out.println("In " + name + ", count is " + i);
      }
    } catch (InterruptedException exc) {
      System.out.println(name + " interrupted");
    }
    System.out.println(name + " terminating.");
  }
}

class UseThreads {
  public static void main(String[] args) {
    System.out.println("Main thread starting.");

    // First, construct a MyThread object
    MyThread mt = new MyThread("Child#1"); // Create a runnable object

    // Next, construct a thread from that object
    Thread newThread = new Thread(mt, "Child child thread");
    System.out.println("Thread :" + newThread.getName());

    // Finally, start execution of the thread
    newThread.start(); // start running the thread

    for (int i = 0; i < 50; i++) {
      System.out.println(".");
      try {
        Thread.sleep(100);
      } catch (InterruptedException exc) {
        System.out.println("Main thread interrupted");
      }
    }

    System.out.println("Main thread ending.");
  }
}
