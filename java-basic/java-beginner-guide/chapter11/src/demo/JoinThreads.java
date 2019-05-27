package demo;

public class JoinThreads {
  public static void main(String[] args) {
    ExtendThread mt3 = ExtendThread.createAndStart("Child #3");
    ExtendThread mt2 = ExtendThread.createAndStart("Child #2");
    ExtendThread mt1 = ExtendThread.createAndStart("Child #1");

    try {
      mt1.join(); // wait until the specified thread ends
      System.out.println("Child #1 joined");
      mt2.join(); // wait until the specified thread ends
      System.out.println("Child #2 joined");
      mt3.join(); // wait until the specified thread ends
      System.out.println("Child #3 joined");
    } catch (InterruptedException exc) {
      System.out.println("Main thread interrupted");
    }
    System.out.println("Main thread ending");
  }
}
