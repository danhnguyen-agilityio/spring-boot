package com.agility.prospring4.annotation.componentscan;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        BeanA beanA = (BeanA) context.getBean("beanA");
        beanA.getBeanB().print();
    }
}
