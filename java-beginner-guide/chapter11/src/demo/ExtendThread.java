package demo;

public class ExtendThread extends Thread {
  ExtendThread(String name) {
    super(name);
  }

  /**
   * Entry point of thread
   */
  public void run() {
    System.out.println(getName() + " starting.");
    try {
      for (int count = 0; count < 10; count++) {
        Thread.sleep(400);
        System.out.println("In " + getName() + ", count is " + count);
      }
    } catch (InterruptedException exc) {
      System.out.println(getName() + " interrupted");
    }
    System.out.println(getName() + " terminating");
  }

  public static ExtendThread createAndStart(String name) {
    ExtendThread extendThread = new ExtendThread(name);
    extendThread.start();
    return extendThread;
  }
}

class Main {
  public static void main(String[] args) {
    System.out.println("Main thread starting.");

    ExtendThread mt1 = ExtendThread.createAndStart("Child #1");
    ExtendThread mt2 = ExtendThread.createAndStart("Child #2");
    ExtendThread mt3 = ExtendThread.createAndStart("Child #3");

    do {
      System.out.println(".");
      try {
        Thread.sleep(100);
      } catch (InterruptedException exc) {
        System.out.println("Main thread interrupted");
      }
    } while (mt1.isAlive() ||
             mt2.isAlive() ||
             mt3.isAlive());

    System.out.println("Main thread ending.");
  }
}
