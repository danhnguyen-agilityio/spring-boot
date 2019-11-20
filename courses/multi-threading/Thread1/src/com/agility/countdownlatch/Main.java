package com.agility.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Processer implements Runnable {
    private CountDownLatch latch;

    public Processer(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Started...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // decrease count down for 1
        latch.countDown();
    }
}

public class Main {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0 ; i < 3; i++) {
            executorService.submit(new Processer(latch));
        }

        try {
            // Wait until count down = 0
            // that mean app will run later command until latch.countDown() is called 3 times
            // Causes the current thread to wait until the latch has counted down to zero
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed");
    }
}
