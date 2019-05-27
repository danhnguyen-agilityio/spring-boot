package demo;

class Thread1 implements Runnable {
  Thread thread;
  boolean suspended; // suspends thread when true
  boolean stopped; // stops thread when true

  Thread1(String name) {
    thread = new Thread(this, name);
    suspended = false;
    stopped = false;
  }

  public static Thread1 createAndStart(String name) {
    Thread1 myThrd= new Thread1(name);
    myThrd.thread.start();
    return myThrd;
  }

  public void run() {
    System.out.println(thread.getName() + " starting.");
    try {
      for (int i = 1; i < 1000; i++) {
        System.out.print(i + " ");
        if ((i % 10) == 0) {
          System.out.println();
          Thread.sleep(250);
        }

        // use synchronized block to check suspended and stopped
        synchronized (this) {
          while (suspended) {
            wait();
          }
          if (stopped) break;
        }
      }
    } catch (InterruptedException exc) {
      System.out.println(thread.getName() + " interrupted");
    }
    System.out.println(thread.getName() + " exiting");
  }

  // Stop the thread
  synchronized void myStop() {
    stopped = true;

    // The following ensures that a suspended thread can be stopped
    suspended = false;
    notify();
  }

  // suspend the thread
  synchronized void mysyspend() {
    suspended = true;
  }

  // resume the thread
  synchronized void myresume() {
    suspended = false;
    notify();
  }
}

public class SuspendingResumingStopping {
  public static void main(String[] args) {
    Thread1 mt1 = Thread1.createAndStart("My Thread");

    try {
      Thread.sleep(1000);

      mt1.mysyspend();
      System.out.println("Suspending thread.");
      Thread.sleep(1000);

      mt1.myresume();
      System.out.println("Resuming thread");
      Thread.sleep(1000);

      mt1.mysyspend();
      System.out.println("Suspending thread");
      Thread.sleep(1000);

      mt1.myresume();
      System.out.println("Resuming thread");
      Thread.sleep(1000);

      mt1.mysyspend();
      System.out.println("Stopping thread");
      mt1.myStop();
    } catch (InterruptedException exc) {
      System.out.println("Main thread interrupted");
    }

    // wait for thread to finish
    try {
      mt1.thread.join();
    } catch (InterruptedException exc) {
      System.out.println("main thread interrupted");
    }

    System.out.println("Main thread exiting. ");
  }
}
