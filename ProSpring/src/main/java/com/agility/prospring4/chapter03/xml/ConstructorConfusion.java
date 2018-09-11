package com.agility.prospring4.chapter03.xml;

import org.springframework.context.support.GenericXmlApplicationContext;

public class ConstructorConfusion {
  private String someValue;

  public ConstructorConfusion(String someValue) {
    System.out.println("Constructor String called");
    this.someValue = someValue;
  }

  public ConstructorConfusion(int someValue) {
    System.out.println("Constructor Int called");
    this.someValue = "Number: " + Integer.toString(someValue);
  }

  @Override
  public String toString() {
    return someValue;
  }

  public static void main(String[] args) {
    GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
    ctx.load("META-INF/app-context-xml.xml");
    ctx.refresh();

    ConstructorConfusion cc = ctx.getBean("constructorConfusion", ConstructorConfusion.class);
    System.out.println(cc);
  }
}
