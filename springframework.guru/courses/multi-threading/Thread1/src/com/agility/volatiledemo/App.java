package com.agility.volatiledemo;

import java.util.Scanner;

class Processor extends Thread {

    // Use volatile keyword to prevent other thread change this value of this thread
    // remove volatile keyword to check result
    private volatile boolean running = true;

    @Override
    public void run() {

        while (running) {
            System.out.println("hello");

            try {
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}

public class App {
    public static void main(String[] args) {
        Processor processor = new Processor();
        processor.start();

        System.out.println("Press return to stop ...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        processor.shutdown();
    }

}
