package demo;

class TickTock {
  String state; // contains the state of the clock

  synchronized  void tick(boolean running) {
    if (!running) { // stop the clock
      state = "ticked";
      notify(); // notify any waiting threads
      return;
    }

    System.out.println("Tick");

    state = "ticked"; // set the current state to ticked

    notify(); // let tock() run
    try {
      Thread.sleep(1000);
      while (!state.equals("tocked")) {
        wait(); // wait for tock() to complete
      }
    } catch (InterruptedException exc) {
      System.out.println("Thread interrupted");
    }
  }

  synchronized  void tock(boolean running) {
    if (!running) { // stop the clock
      state = "tocked";
      notify(); // notify any waiting threads
      return;
    }

    System.out.println("Tock");

    state = "tocked"; // set the current state to tocked

    notify(); // let tick() run
    try {
      Thread.sleep(1000);
      while (!state.equals("ticked")) {
        wait(); // wait for tick to complete
      }
    } catch (InterruptedException exc) {
      System.out.println("Thread interrupted");
    }
  }

}

class OurThread implements Runnable {
  Thread thread;
  TickTock tickTock;

  OurThread(String name, TickTock tt) {
    thread = new Thread(this, name);
    tickTock = tt;
  }

  public static OurThread createAndStart(String name, TickTock tt) {
    OurThread ourThread = new OurThread(name, tt);
    ourThread.thread.start();
    return ourThread;
  }

  public void run() {
    if (thread.getName().compareTo("Tick") == 0) {
      for (int i = 0; i < 5 ; i++) {
        tickTock.tick(true);
      }
      tickTock.tick(false);
    } else {
      for (int i = 0; i < 5; i++) {
        tickTock.tock(true);
      }
      tickTock.tock(false);
    }
  }
}

public class WaitNotify {
  public static void main(String[] args) {
    TickTock tickTock = new TickTock();
    OurThread mt1 = OurThread.createAndStart("Tick", tickTock);
    OurThread mt2 = OurThread.createAndStart("Tock", tickTock);

    try {
      mt1.thread.join();
      mt2.thread.join();
    } catch (InterruptedException exc) {
      System.out.println("Interrupted main thread");
    }
  }
}
