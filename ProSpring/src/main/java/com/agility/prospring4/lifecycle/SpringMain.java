package com.agility.prospring4.lifecycle;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {
  public static void main(String[] args) {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/lifecycle.xml");

    System.out.println("Spring Context initialized");

//    EmployeeService service = (EmployeeService) context.getBean("employeeService");

    context.close();
  }
}
