package com.agility.low_level_sync;

import java.util.LinkedList;
import java.util.Random;

class Processor {
    private LinkedList<Integer> list = new LinkedList<>();
    private final int LIMIT = 10;
    private Object lock = new Object();

    public void produce() throws InterruptedException {
        int value = 0;

        while (true) {
            synchronized (lock) {
                System.out.println("produce");
                while (list.size() == LIMIT) {
                    lock.wait();
                }

                list.add(value++);
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        Random random = new Random();

        while (true) {
            synchronized (lock) {
                System.out.println("consume");
                while (list.size() == 0) {
                    lock.wait();
                }

                System.out.print("List size is: " + list.size());
                int value = list.removeFirst();
                System.out.println(";, value is: " + value);
                lock.notify();
            }

            Thread.sleep(random.nextInt(1000));
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final Processor processor = new Processor();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

}
