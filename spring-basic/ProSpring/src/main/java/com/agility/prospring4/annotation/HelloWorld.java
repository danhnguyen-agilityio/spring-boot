package com.agility.prospring4.annotation;

import com.agility.prospring4.annotation.qualifier.BeanInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class HelloWorld {
    // Can autowired on private property
//    @Autowired
    PrintService printService;
    ConsoleService consoleService;
    BeanInterface bean;

    public void print() {
        System.out.println("Hello world");
    }

    @Autowired
    public void setPrintService(PrintService printService) {
        this.printService = printService;
        this.consoleService = printService.getConsoleService();
    }

    @Autowired
    @Qualifier("bean2")
    public void setBean(BeanInterface bean) {
        this.bean = bean;
    }
}
