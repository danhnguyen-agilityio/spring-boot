package com.agility.prospring4.lombok;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Student student = new Student("Danh", "12", new Date());
        student.setCode("20");
        System.out.println(student.toString());
    }
}
