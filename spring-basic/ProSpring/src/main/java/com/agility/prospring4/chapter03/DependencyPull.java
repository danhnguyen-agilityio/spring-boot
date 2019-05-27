package com.agility.prospring4.chapter03;

import com.agility.prospring4.chapter02.MessageRenderer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DependencyPull {
  public static void main(String[] args) {
    ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "META-INF/app-context.xml"
    );

    MessageRenderer mr = ctx.getBean("renderer", MessageRenderer.class);
    mr.render();
  }
}
