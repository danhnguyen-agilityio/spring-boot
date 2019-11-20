package com.in28minute.jpa.hibernate.jpahibernate.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String line1;
    private String line2;
    private String address;

    public Address() {
    }

    public Address(String line1, String line2, String address) {
        this.line1 = line1;
        this.line2 = line2;
        this.address = address;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
