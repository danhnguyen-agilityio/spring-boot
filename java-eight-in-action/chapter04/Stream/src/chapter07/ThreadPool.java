package chapter07;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class WorkerThread implements Runnable {
  private String message;
  public WorkerThread(String s) {
    message = s;
  }

  public void run() {
    System.out.println(Thread.currentThread().getName() + " (Start) message = " + message);
    processmessage();
    System.out.println(Thread.currentThread().getName() + " (End)");
  }

  private void processmessage() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException exc) {
      exc.printStackTrace();
    }
  }
}

public class ThreadPool {
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(5); // create a poll of 5 threads
    for (int i = 0; i < 10; i++) {
      Runnable worker = new WorkerThread("" + i);
      executorService.execute(worker); // calling execute method of ExecutorService
    }

    executorService.shutdown(); // close thread pool
    while (!executorService.isTerminated()) {}

    System.out.println("Finished all thread");
  }
}
