package com.agility.springbeanscopeannotation;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

//@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
@RequestScope
public class Address {
    private String address = "US";

    public Address() {
        System.out.println("Create new Address: " + this.address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}