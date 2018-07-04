package demo;

class OtherThread implements Runnable {
  Thread thread;

  // Construct a new thread using this Runnable and give it a name
  public OtherThread(String name) {
    thread = new Thread(this, name); // the thread is named when it is created
  }

  // A factory method that creates and starts a thread
  public static OtherThread createAndStart(String name) {
    OtherThread otherThread = new OtherThread(name);
    otherThread.thread.start(); // start the thread
    return otherThread;
  }

  // Entry point of thread
  public void run() {
    System.out.println(thread.getName() + " starting.");
    try {
      for (int i = 0; i < 10; i++) {
        Thread.sleep(400);
        System.out.println("In " + thread.getName() + ", count is " + i);
      }
    } catch (InterruptedException exc) {
      System.out.println(thread.getName() + " interrupted");
    }
    System.out.println(thread.getName() + " terminating.");

  }
}

public class ThreadVariation {
  public static void main(String[] args) {
    System.out.println("Main thread starting.");

    // Create and start a thread
    OtherThread mt = OtherThread.createAndStart("Child #1");

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
