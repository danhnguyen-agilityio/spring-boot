package chapter11;

import java.util.concurrent.*;

public class FuturesDemo {
  public void futureDemo() {
    // Create an ExecutorService allowing you to submit tasks to a thread pool
    ExecutorService executorService = Executors.newCachedThreadPool();

    // Submit a Callable to the ExecutorService
    Future<Double> future = executorService.submit(new Callable<Double>() {
      @Override
      public Double call() throws Exception {
        // Execute a long operation asynchronously in a separate thread
        return doSomeLongComputation();
      }
    });

    // Do something else while the asynchronous operation is progressing
    doSomethingElse();

    try {
      System.out.println("Before computation");
      // Retrieve the result of the asynchronous operation
      // eventually blocking if it isn't available yet,
      // but waiting at most for 1 second
      Double result = future.get(1, TimeUnit.SECONDS);
      System.out.println("After computation");
    } catch (ExecutionException ee) {
      ee.printStackTrace();
      // the computation threw an exception
    } catch (InterruptedException ie) {
      ie.printStackTrace();
      // the current thread was interrupted while waiting
    } catch (TimeoutException te) {
      te.printStackTrace();
      // the timeout expired before the Future completion
    }
  }

  private void doSomethingElse() {
    System.out.println("doSomethingElse");
  }

  private Double doSomeLongComputation() {
    System.out.println("doSomeLongComputation");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return 1000D;
  }

  public static void main(String[] args) {
    FuturesDemo futuresDemo = new FuturesDemo();
    futuresDemo.futureDemo();
  }

}
