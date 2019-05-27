package com.agility.prospring4.annotation.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DataConfig.class);
        PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager) context.getBean("manager");

        platformTransactionManager.getDataSource().print();
    }
}
