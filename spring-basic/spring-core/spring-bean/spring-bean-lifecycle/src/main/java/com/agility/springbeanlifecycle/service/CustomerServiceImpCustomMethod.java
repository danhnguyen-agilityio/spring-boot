package com.agility.springbeanlifecycle.service;

import com.agility.springbeanlifecycle.bean.Customer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service(value = "customerServiceImpCustomMethod")
public class CustomerServiceImpCustomMethod implements InitializingBean, DisposableBean {
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public CustomerServiceImpCustomMethod() {
        System.out.println("Call constructor for CustomerServiceImpCustomMethod");
    }

    @Autowired
    public void setCustomer(Customer customer) {
        this.customer = customer;
        System.out.println("setCustomer(): perform after constructor method");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println(
            "post-construct():  perform some initialization after all the setter methods have been called");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet(): Bean initialization here");
    }

    public void customInitBean() throws Exception {
        System.out.println("customInitBean()");
    }

    // print console in Main

    @PreDestroy
    public void predestroy() {
        System.out.println("pre-destroy()");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy(): Bean destruction here");
    }

    public void customDestroyBean() throws Exception {
        System.out.println("customDestroyBean()");
    }

}
