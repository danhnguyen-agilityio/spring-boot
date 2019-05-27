package com.agility.springannotation.qualifier.bean;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
//@Qualifier("manager")
public class Manager implements Person {
    @Override
    public String info() {
        return "Manager";
    }
}
