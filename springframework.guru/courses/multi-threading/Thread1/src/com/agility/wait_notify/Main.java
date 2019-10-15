package com.agility.wait_notify;

import java.util.Scanner;

class Processor {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running ...");

            // Wait until another thread call notify method();
            wait();

            System.out.println("Resumed");
        }
    }

    public void consume() throws InterruptedException {
        synchronized (this) {
            Scanner scanner = new Scanner(System.in);
            Thread.sleep(2000);

            System.out.println("Waiting for return key");
            scanner.nextLine();
            System.out.println("Return key pressed");
            notify();
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
