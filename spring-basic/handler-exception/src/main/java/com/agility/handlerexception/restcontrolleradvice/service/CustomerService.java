package com.agility.handlerexception.restcontrolleradvice.service;

import com.agility.handlerexception.restcontrolleradvice.model.Customer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
public class CustomerService {

    HashMap<String, Customer> custStorage = new HashMap<>();

    @PostConstruct
    void init() {
        Customer jack = new Customer("Jack", 20);
        Customer peter = new Customer("Peter", 30);
        custStorage.put("Jack", jack);
        custStorage.put("Peter", peter);
    }

    public Customer findCustomerByName(String name) {
        return custStorage.get(name);
    }
}
