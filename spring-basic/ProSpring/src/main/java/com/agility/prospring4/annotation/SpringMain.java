package com.agility.prospring4.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringMain {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        HelloWorld helloWorld = (HelloWorld) context.getBean("helloWorld");
        helloWorld.print();
        helloWorld.printService.print();
        helloWorld.consoleService.print();
        helloWorld.bean.print();

    }

}
