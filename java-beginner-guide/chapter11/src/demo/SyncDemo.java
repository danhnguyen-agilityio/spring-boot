package demo;

/**
 * Use synchronize to control access
 */
class SumArray {
  private int sum;

//  synchronized int sumArray(int nums[]) {
  int sumArray(int nums[]) {
    sum = 0; // reset sum

    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      System.out.println("Running total for " + Thread.currentThread().getName() + " is " + sum + ", num is " + nums[i]);
      try {
        Thread.sleep(10); // allow task switch
      } catch (InterruptedException exc) {
        System.out.println("Thread interrupted");
      }
    }
    return sum;
  }
}

class MyThreadSync implements Runnable {
  Thread thread;
  static SumArray sa = new SumArray();
  int a[];
  int answer;

  // Construct a new thread
  MyThreadSync(String name, int nums[]) {
    thread = new Thread(this, name);
    a = nums;
  }

  public static MyThreadSync createAndStart(String name, int nums[]) {
    MyThreadSync myThreadSync= new MyThreadSync(name, nums);
    myThreadSync.thread.start();
    return myThreadSync;
  }

  public void run() {
    int sum;
    System.out.println(thread.getName() + " starting");
    synchronized (sa) {
      answer = sa.sumArray(a);
    }
    System.out.println("Sim for " + thread.getName() + " is " + answer);
    System.out.println(thread.getName() + " terminating");
  }

}

public class SyncDemo {
  public static void main(String[] args) {
    int a[] = {1,2,3,4,5};
    MyThreadSync mt1 = MyThreadSync.createAndStart("Child #1", a);
    MyThreadSync mt2 = MyThreadSync.createAndStart("Child #2", a);
    try {
      mt1.thread.join();
      mt2.thread.join();
    } catch (InterruptedException exc) {
      System.out.println("Main thread interrupted");
    }
  }
}
