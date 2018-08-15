package com.agility.prospring4.chapter03.annotation;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Collection injection (annotation)
 */
@Service("injectCollection")
public class CollectionInjection {

  @Resource(name="map")
  private Map<String, Object> map;

  @Resource(name="props")
  private Properties props;

  @Resource(name="set")
  private Set set;

  @Resource(name="list")
  private List list;

  public void displayInfo() {
    System.out.println("Map contents:\n");
    for (Map.Entry<String, Object> entry: map.entrySet()) {
      System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
    }

    System.out.println("\nProperties contents:\n");
    for (Map.Entry<Object, Object> entry: props.entrySet()) {
      System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
    }

    System.out.println("\nSet contents:\n");
    for (Object obj: set) {
      System.out.println("Value: " + obj);
    }

    System.out.println("\nList contents:\n");
    for (Object obj: list) {
      System.out.println("Value: " + obj);
    }
  }

  public static void main(String[] args) {
    GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
    ctx.load("classpath:META-INF/app-context-annotation.xml");
    ctx.refresh();

    CollectionInjection instance = (CollectionInjection) ctx.getBean("injectCollection");
    instance.displayInfo();
  }
}
