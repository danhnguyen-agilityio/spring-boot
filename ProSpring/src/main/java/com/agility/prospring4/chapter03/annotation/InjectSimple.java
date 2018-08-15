package com.agility.prospring4.chapter03.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service("injectSimple")
public class InjectSimple {
  @Value("David Nguyen")
  private String name;

  @Value("32")
  private int age;

  @Value("1.778")
  private float height;

  @Value("true")
  private boolean programmer;

  @Value("1009843200")
  private Long ageInSeconds;

  @Override
  public String toString() {
    return "Name :" + name + "\n"
        + "Age:" + age + "\n"
        + "Age in Seconds: " + ageInSeconds + "\n"
        + "Height: " + height + "\n"
        + "Is Programmer?: " + programmer;
  }

  public static void main(String[] args) {
    GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
    ctx.load("META-INF/app-context-annotation.xml");
    ctx.refresh();

    InjectSimple simple = ctx.getBean("injectSimple", InjectSimple.class);
    System.out.println(simple);
  }
}
