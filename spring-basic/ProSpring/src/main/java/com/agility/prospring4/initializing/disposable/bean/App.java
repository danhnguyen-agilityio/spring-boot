package com.agility.prospring4.initializing.disposable.bean;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
  public static void main(String[] args) {
    ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring-customer.xml");

    CustomService customService = (CustomService) context.getBean("customerService");

    System.out.println(customService);

    context.close();
  }
}
