package chapter11;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Shop {

  Shop(String name) {

  }

  public double getPrice(String product) {
    return calculatePrice(product);
  }

  public Future<Double> getPriceAsync(String product) {
    System.out.println("Async method");
    // Create CompletableFuture that will contain the result of the computation
    CompletableFuture<Double> futurePrice = new CompletableFuture<>();
    new Thread(() -> {
      try {
        // Execute the computation async in a different Thread
        double price = calculatePrice(product);
        // Set the value returned by the long computation on the Future when it becomes available
        futurePrice.complete(price);
      } catch (Exception ex) {
        // Otherwise, complete it exceptionally with the Exception that caused the failure
        futurePrice.completeExceptionally(ex);
      }
    }).start();
    return futurePrice;
  }

  private double calculatePrice(String product) {
    delay();
    System.out.println("Computation");
    return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
  }

  public static void delay() {
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    Shop shop = new Shop("Best shop");
    long start = System.nanoTime();
    Future<Double> futurePrice = shop.getPriceAsync("my favorite");
    long invocationTime = (System.nanoTime() - start) / 1_000_000;
    System.out.println("Invocation returned after " + invocationTime + " msecs");

    // Do some more tasks, like querying other shops
    doSomethingElse();

    try {
      // Read the price from the Future or block until it won't available
      double price = futurePrice.get(100, TimeUnit.MILLISECONDS);
      System.out.printf("Price is %.2f%n", price);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    long retrievalTime = (System.nanoTime() - start) / 1_000_000;
    System.out.println("Price returned after " + retrievalTime + " msecs");
  }

  private static void doSomethingElse() {
    System.out.println("Sync method");
  }
}
