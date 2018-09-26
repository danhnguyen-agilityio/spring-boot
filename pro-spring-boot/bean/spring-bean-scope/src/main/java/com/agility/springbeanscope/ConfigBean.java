package com.agility.springbeanscope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ConfigBean {

    @Bean
    @Scope("singleton")
    public Name name1() {
        return new Name("Jack", "Smith");
    }

    @Bean
    @Scope("prototype")
    public Name name2() {
        return new Name("Adam", "Johnson");
    }

    @Bean
    public Customer customer1() {
        Customer customer = new Customer();
        customer.setName(name1());
        return customer;
    }

    @Bean
    public Customer customer2() {
        Customer customer = new Customer();
        customer.setName(name1());
        return customer;
    }

    @Bean
    public Customer customer3() {
        Customer customer = new Customer();
        customer.setName(name2());
        return customer;
    }

    @Bean
    public Customer customer4() {
        Customer customer = new Customer();
        customer.setName(name2());
        return customer;
    }
}
