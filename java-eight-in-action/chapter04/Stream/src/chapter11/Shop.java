package chapter11;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Shop {



  private String name;

  Shop(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice(String product) {
    return calculatePrice(product);
  }

  public Future<Double> getPriceAsync1(String product) {
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

  public Future<Double> getPriceAsync(String product) {
    return CompletableFuture.supplyAsync(() -> calculatePrice(product));
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

  private static void doSomethingElse() {
    System.out.println("Sync method");
  }

  public List<String> findPrices(String product) {
    List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
        new Shop("Lets Save Big"),
        new Shop("My Favorite Shop"),
        new Shop("Flower Shop"),
        new Shop("Buy It All"));

    // Use a parallel stream to retrieve the prices from the different shops in parallel
    return shops.parallelStream()
        .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
        .collect(Collectors.toList());
  }

  public List<String> findPricesAsync(String product) {
    List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
        new Shop("Lets Save Big"),
        new Shop("My Favorite Shop"),
        new Shop("Flower Shop"),
        new Shop("Buy It All"));

    List<CompletableFuture<String>> priceFutures = shops.stream()
        // Calculate each price async with a CompletableFuture
        .map(shop -> CompletableFuture.supplyAsync(
            () -> String.format("%s price is %.2f",
                shop.getName(), shop.getPrice(product))))
         .collect(Collectors.toList());

    return priceFutures.stream()
        // Waiting for the completion of all async operation
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
  }

  public void performanceAsyncMethod() {
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

  public void performanceFindPrices() {
    long start = System.nanoTime();
//    System.out.println(findPrices("my phone"));
    System.out.println(findPricesAsync("my phone"));
    long duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println("Done in " + duration + " msecs");
  }

  public static void main(String[] args) {
    Shop shop = new Shop("Demo");
    shop.performanceFindPrices();
    System.out.println(Runtime.getRuntime().availableProcessors());
  }

}
