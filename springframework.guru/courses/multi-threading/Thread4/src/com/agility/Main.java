package com.agility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private Random random = new Random();

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    public void stageOne(Runnable runnable) {
        System.out.println("Stage one out" + runnable.toString());
        synchronized (lock1) {
            System.out.println("Stage one:" + runnable.toString());
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list1.add(random.nextInt(100));
        }
    }

    public void stageTwo(Runnable runnable) {
        System.out.println("Stage two out:" + runnable.toString());
        synchronized (lock2) {
            System.out.println("Stage two:" + runnable.toString());
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list2.add(random.nextInt(100));
        }
    }

    public static void main(String[] args) {
	    Main main = new Main();

        System.out.println("Starting...");
	    long start = System.currentTimeMillis();

	    Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    main.stageOne(this);
                    main.stageTwo(this);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    main.stageOne(this);
                    main.stageTwo(this);
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("Time take:" + (end - start));
    }
}
