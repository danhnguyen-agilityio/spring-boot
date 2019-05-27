package com.agility.prospring4.chapter03.xml;

import org.springframework.context.support.GenericXmlApplicationContext;

public class InjectSimple {
  private String name;
  private int age;
  private float height;
  private boolean programmer;
  private Long ageInSeconds;

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public void setProgrammer(boolean programmer) {
    this.programmer = programmer;
  }

  public void setAgeInSeconds(Long ageInSeconds) {
    this.ageInSeconds = ageInSeconds;
  }

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
    ctx.load("META-INF/app-context-xml.xml");
    ctx.refresh();

    InjectSimple simple = ctx.getBean("injectSimple", InjectSimple.class);
    System.out.println(simple);
  }
}
