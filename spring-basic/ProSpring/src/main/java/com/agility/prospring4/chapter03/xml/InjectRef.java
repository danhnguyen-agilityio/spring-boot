package com.agility.prospring4.chapter03.xml;

import com.agility.prospring4.chapter03.beanfactory.Oracle;
import org.springframework.context.support.GenericXmlApplicationContext;

public class InjectRef {
  private Oracle oracle;

  public void setOracle(Oracle oracle) {
    this.oracle = oracle;
  }

  @Override
  public String toString() {
    return oracle.defineMeaningOfLife();
  }

  public static void main(String[] args) {
    GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
    ctx.load("classpath:META-INF/app-context-xml.xml");
    ctx.refresh();

    InjectRef injectRef = (InjectRef) ctx.getBean("injectRef");
    System.out.println(injectRef);
  }
}
