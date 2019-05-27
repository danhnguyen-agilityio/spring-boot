package com.agility.prospring4.chapter03.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Inject values by using SpEL (Annotation)
 */
@Service("injectSimpleSpel")
public class InjectSimpleSpel {
  @Value("#{injectSimpleConfig.name}")
  private String name;

  @Value("#{injectSimpleConfig.age + 1}")
  private int age;

  @Value("#{injectSimpleConfig.height}")
  private float height;

  @Value("#{injectSimpleConfig.programmer}")
  private boolean programmer;

  @Value("#{injectSimpleConfig.ageInSeconds}")
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

    InjectSimpleSpel simple = ctx.getBean("injectSimpleSpel", InjectSimpleSpel.class);
    System.out.println(simple);
  }
}
