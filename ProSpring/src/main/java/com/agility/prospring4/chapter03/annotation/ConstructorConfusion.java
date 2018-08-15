package com.agility.prospring4.chapter03.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service("constructorConfusion")
public class ConstructorConfusion {
  private String someValue;

  public ConstructorConfusion(String someValue) {
    System.out.println("ConstructorConfusion String called");
    this.someValue = someValue;
  }

  @Autowired
  public ConstructorConfusion(@Value("90") int someValue) {
    System.out.println("ConstructorConfusion Int called");
    this.someValue = "Number: " + Integer.toString(someValue);
  }

  @Override
  public String toString() {
    return someValue;
  }

  public static void main(String[] args) {
    GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
    ctx.load("META-INF/app-context-annotation.xml");
    ctx.refresh();

    ConstructorConfusion cc = ctx.getBean("constructorConfusion", ConstructorConfusion.class);
    System.out.println(cc);
  }
}
