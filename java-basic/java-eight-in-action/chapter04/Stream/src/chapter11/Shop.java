package chapter11;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

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

  public String getPriceOther(String product) {
    double price = calculatePrice(product);
    Discount.Code code = Discount.Code.values()[new Random().nextInt(Discount.Code.values().length)];
    return String.format("%s:%.2f:%s", name, price,code);
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
//    System.out.println("Computation");
    return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
  }

  public static void delay() {
    int delay = 500 + new Random().nextInt(2000);
    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      throw new RuntimeException();
    }
  }

  /**
   * Random delay between 0.5 and 2.5 seconds
   */
  public static void randomDelay() {
    int delay = 500 + new Random().nextInt(2000);
    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      throw new RuntimeException();
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
        .collect(toList());
  }

  public List<String> findPricesOther(String product) {
    List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
        new Shop("Lets Save Big"),
        new Shop("My Favorite Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Buy It All"));

    // Create a thread pool with a number of threads equal to the minimum between 100 and the number of shops
    Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        // Use daemon threads - prevent the termination of the program
        t.setDaemon(true);
        return t;
      }
    });

    List<CompletableFuture<String>> priceFutures =
    shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceOther(product), executor))
        .map(future -> future.thenApply(Quote::parse))
        // Compose the resulting Future with another async task, applying the discount code
        .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(
            () -> Discount.applyDiscount(quote), executor
        )))
        .collect(toList());

    return priceFutures.stream()
        // Wait for all the Futures in the stream to be completed and extract their respective results
        .map(CompletableFuture::join)
        .collect(toList());

  }

  public List<String> findPricesAsync(String product) {
    List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
        new Shop("Lets Save Big"),
        new Shop("My Favorite Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Buy It All"));

    // Create a thread pool with a number of threads equal to the minimum between 100 and the number of shops
    Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        // Use daemon threads - prevent the termination of the program
        t.setDaemon(true);
        return t;
      }
    });

    List<CompletableFuture<String>> priceFutures = shops.stream()
        // Calculate each price async with a CompletableFuture
        .map(shop -> CompletableFuture.supplyAsync(
            () -> String.format("%s price is %.2f",
                shop.getName(), shop.getPrice(product)), executor))
         .collect(toList());

    return priceFutures.stream()
        // Waiting for the completion of all async operation
        .map(CompletableFuture::join)
        .collect(toList());
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

  public Double futurePrieceInUSD(String product) throws Exception {
    Future<Double> futurePriceInUSD = CompletableFuture.supplyAsync(() -> getPrice(product))
        .thenCombine(CompletableFuture.supplyAsync(
            () -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)
        ), (price, rate) -> price * rate);

    return ((CompletableFuture<Double>) futurePriceInUSD).join();
  }

  /**
   * Combining two Futures in Java 7
   * @param product
   * @return
   */
  public Double futurePrieceInUSDInJava7(String product) throws Exception {
    ExecutorService executorService = Executors.newCachedThreadPool();
    final Future<Double> futureRate = executorService.submit(new Callable<Double>() {
      @Override
      public Double call() throws Exception {
        return ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD);
      }
    });

    Future<Double> futurePriceInUSD = executorService.submit(new Callable<Double>() {
      @Override
      public Double call() throws Exception {
        double price = getPrice(product);
        return price * futureRate.get();
      }
    });

    return futurePriceInUSD.get();
  }

  public Stream<CompletableFuture<String>> findPricesStream(String product) {
    List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
        new Shop("Lets Save Big"),
        new Shop("My Favorite Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Flower Shop"),
        new Shop("Buy It All"));

    // Create a thread pool with a number of threads equal to the minimum between 100 and the number of shops
    Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        // Use daemon threads - prevent the termination of the program
        t.setDaemon(true);
        return t;
      }
    });

    return shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceOther(product), executor))
        .map(future -> future.thenApply(Quote::parse))
        // Compose the resulting Future with another async task, applying the discount code
        .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(
            () -> Discount.applyDiscount(quote), executor
        )));

  }

  public void performanceFindPrices() {
    long start = System.nanoTime();
//    System.out.println(findPrices("my phone"));
//    System.out.println(findPricesAsync("my phone"));
    System.out.println(findPricesOther("my phone"));
    long duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println("Done in " + duration + " msecs");
  }

  public static void main(String[] args) throws Exception {
    Shop shop = new Shop("Demo");
//    shop.performanceFindPrices();
    System.out.println(Runtime.getRuntime().availableProcessors());

//    System.out.println("Price in USD :" + shop.futurePrieceInUSD("Demo"));
//    System.out.println("Price in USD :" + shop.futurePrieceInUSDInJava7("Demo"));

    long start = System.nanoTime();
    CompletableFuture[] futures = shop.findPricesStream("myPhone")
        .map(f -> f.thenApply(s -> {
          long duration = (System.nanoTime() - start) / 1_000_000;
          System.out.println(s + " (done in " + duration + " msecs");
          return s;
        }))
        .toArray(size -> new CompletableFuture[size]);
    CompletableFuture.allOf(futures).join(); // return CompletableFuture<void>
    long duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println("All shops have now responded in " + duration + " msecs");
  }

}
