package com.agility.prospring4.chapter03;

import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Nesting Generic
 */
public class HierarchicalAppContextUsage {

  public static void main(String[] args) {
    GenericXmlApplicationContext parent = new GenericXmlApplicationContext();
    parent.load("META-INF/parent.xml");
    parent.refresh();

    GenericXmlApplicationContext child = new GenericXmlApplicationContext();
    child.load("META-INF/child.xml");
    child.setParent(parent);
    child.refresh();

    SimpleTarget target1 = (SimpleTarget) child.getBean("target1");
    SimpleTarget target2 = (SimpleTarget) child.getBean("target2");
    SimpleTarget target3 = (SimpleTarget) child.getBean("target3");

    System.out.println(target1.getVal());
    System.out.println(target2.getVal());
    System.out.println(target3.getVal());
  }
}
