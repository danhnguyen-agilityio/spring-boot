package com.agility.thread3;

public class Main {

    private int count = 0;

    private synchronized void increment() {
        count++;
    }

    public static void main(String[] args) {
        com.agility.Main main = new com.agility.Main();
        main.doWork();
    }

    public void doWork() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increment();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increment();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        }

        // Execute this command when thread t1 and t2 finish
        System.out.println("Count: " + count);
    }
}
