package com.agility.springannotation.qualifier.config;

import com.agility.springannotation.qualifier.bean.Manager;
import com.agility.springannotation.qualifier.bean.Person;
import com.agility.springannotation.qualifier.bean.Student;
import com.agility.springannotation.qualifier.qualifier.PersonQ;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonFactory {

    @Bean("person1")
//    @Qualifier("student")
    @PersonQ("student")
    public Person createStudent() {
        return new Student();
    }

    @Bean("person2")
//    @Qualifier("manager")
    @PersonQ("manager")
    public Person createManager() {
        return new Manager();
    }

}
