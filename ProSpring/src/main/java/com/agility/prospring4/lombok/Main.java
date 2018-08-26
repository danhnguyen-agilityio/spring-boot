package com.agility.prospring4.lombok;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
//        Student student = new Student("Danh", "12", new Date());
//        student.setCode("20");
//        System.out.println(student.toString());
//
//        Student student1 = new Student();
//        student1.setName("Khanh");
//        student1.setCode("abc");
//
//        Student student2 = new Student();
//        student2.setName("Khanh");
//        student2.setCode("bcd");
//        System.out.println(student1.equals(student2));

        Student student = Student.builder()
            .name("Danh")
            .code("12")
            .dateOfBirth(new Date())
            .build();
        System.out.println(student);
    }
}
