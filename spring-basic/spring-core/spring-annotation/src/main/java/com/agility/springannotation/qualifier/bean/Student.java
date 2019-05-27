package com.agility.springannotation.qualifier.bean;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
//@Qualifier("student")
public class Student implements Person {

    @Override
    public String info() {
        return "Student";
    }
}
