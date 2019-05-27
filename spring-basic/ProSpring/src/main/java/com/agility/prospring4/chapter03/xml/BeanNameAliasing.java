package com.agility.prospring4.chapter03.xml;

import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanNameAliasing {
  public static void main(String[] args) {
    GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
    ctx.load("classpath:META-INF/app-context-xml.xml");
    ctx.refresh();

    String s1 = (String) ctx.getBean("name1");
    String s2 = (String) ctx.getBean("name2");
    String s3 = (String) ctx.getBean("name3");
    String s4 = (String) ctx.getBean("name4");
    String s5 = (String) ctx.getBean("name5");
    String s6 = (String) ctx.getBean("name6");
    String s7 = (String) ctx.getBean("name7");

    String[] aliases = ctx.getAliases("name1");

    System.out.println(s1 == s2);
    System.out.println(s2 == s3);
    System.out.println(s3 == s4);
    System.out.println(s4 == s5);
    System.out.println(s5 == s6);
    System.out.println(s6 == s7);
    for (String s : aliases) {
      System.out.println(s);
    }
  }
}
