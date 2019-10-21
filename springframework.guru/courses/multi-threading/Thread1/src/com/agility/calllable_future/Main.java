package com.agility.calllable_future;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        Future<Integer> future = executor.submit(new Callable<Integer>() {
//        Future<?> future = executor.submit(new Callable<Void>() {
            @Override
            public Integer call() throws Exception {
                Random random = new Random();

                int duration = random.nextInt(4000);

                if (duration > 2000) {
                    throw new IOException("Time too long");
                }

                System.out.println("Starting ...");

                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Finished");

                return duration;
            }
        });

        executor.shutdown();

        try {
            System.out.println("Result is: " + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }
}
