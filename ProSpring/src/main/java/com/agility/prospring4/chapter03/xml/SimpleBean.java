package com.agility.prospring4.chapter03.xml;

import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Bean inheritance
 */
public class SimpleBean {
  private String name;
  private int age;

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "Name: " + name + "\n" + "Age: " + age;
  }

  public static void main(String[] args) {
    GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
    ctx.load("classpath:META-INF/app-context-xml.xml");
    ctx.refresh();

    SimpleBean parent = (SimpleBean) ctx.getBean("inheritParent");
    SimpleBean child = (SimpleBean) ctx.getBean("inheritChild");

    System.out.println("Parent: " + parent);
    System.out.println("Child: " + child);
  }
}
