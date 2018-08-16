package com.agility.prospring4.chapter03.methodinjection;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.util.StopWatch;

public class LookupDemo {
  public static void main(String[] args) {
    GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
    ctx.load("classpath:META-INF/app-context-xml.xml");
    ctx.refresh();

    DemoBean abstractBean = (DemoBean) ctx.getBean("abstractLookupBean");
    DemoBean standardBean = (DemoBean) ctx.getBean("standardLookupBean");

    System.out.println("STANDARD BEAN");
    displayInfo(standardBean);

    System.out.println("ABSTRACT BEAN");
    displayInfo(abstractBean);
  }

  private static void displayInfo(DemoBean bean) {
    MyHelper helper1 = bean.getMyHelper();
    MyHelper helper2 = bean.getMyHelper();

    System.out.println("Helper instances the Same: " + (helper1 == helper2));

    StopWatch stopWatch = new StopWatch();
    stopWatch.start("lookupDemo");

    for (int x = 0; x < 100000; x++) {
      MyHelper helper = bean.getMyHelper();
      helper.doSomethingHelpful();
    }

    stopWatch.stop();

    System.out.println("100000 gets took " + stopWatch.getTotalTimeMillis() + " ms");
  }
}
