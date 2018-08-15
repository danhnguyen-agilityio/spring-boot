package com.agility.prospring4.chapter03.xml;

import org.springframework.context.support.GenericXmlApplicationContext;

public class DeclareSpringComponents {
  public static void main(String[] args) {
    GenericXmlApplicationContext context = new GenericXmlApplicationContext();
    context.load("classpath:META-INF/app-context-xml.xml");
    context.refresh();

    MessageProvider messageProvider = context.getBean("messageProvider", MessageProvider.class);
    System.out.println(messageProvider.getMessage());
  }
}
