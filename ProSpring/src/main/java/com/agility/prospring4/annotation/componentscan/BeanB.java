package com.agility.prospring4.annotation.componentscan;

import org.springframework.stereotype.Service;

@Service("beanB")
public class BeanB {
    public void print() {
        System.out.println("Bean B");
    }
}
