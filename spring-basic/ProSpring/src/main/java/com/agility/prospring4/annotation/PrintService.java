package com.agility.prospring4.annotation;

public class PrintService {
    public void print() {
        System.out.println("Print service");
    }

    public ConsoleService getConsoleService() {
        return new ConsoleService();
    }
}
