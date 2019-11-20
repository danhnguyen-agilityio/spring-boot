package com.agility.reentrantlock;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Runner {
    private int  count = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }


    public void firstThread() throws InterruptedException {
        lock.lock();

        System.out.println("Waiting ...");
        condition.await();

        System.out.println("Woken up!");

        increment();
        lock.unlock();
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();

        System.out.println("Press the return key");
        new Scanner(System.in).nextLine();
        System.out.println("Got return key!");

        condition.signal();

        increment();
        lock.unlock();
    }

    public void finish() throws InterruptedException {
        System.out.println("Total count: " + count);
    }
}


public class Main {
    public static void main(String[] args) throws InterruptedException {
        final Runner runner = new Runner();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.firstThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.secondThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        runner.finish();
    }

}
