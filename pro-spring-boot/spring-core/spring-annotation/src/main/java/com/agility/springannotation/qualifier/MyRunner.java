package com.agility.springannotation.qualifier;

import com.agility.springannotation.qualifier.bean.Person;
import com.agility.springannotation.qualifier.qualifier.PersonQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    // @Qualifier("student")
    @PersonQ("student")
    Person p1;

    @Autowired
    // @Qualifier("manager")
    @PersonQ("manager")
    Person p2;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(p1.info());
        System.out.println(p2.info());
    }
}
