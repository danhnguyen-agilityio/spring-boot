package com.agility.semaphore;

import java.util.concurrent.Semaphore;

public class Connection {

    private static Connection instance = new Connection();

    private int connection = 0;

    private Semaphore sem = new Semaphore(10, true);

    public Connection() {
    }

    public static Connection getInstance() {
        return instance;
    }

    public void connect() {

        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connection++;
            System.out.println("Current connections: " + connection);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connection--;
        }

        sem.release();
    }
}
