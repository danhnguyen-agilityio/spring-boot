package com.agility.handlerexception.restcontrolleradvice.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Customer {

    @NotNull(message = "{user.firstName.notNull}")
    @Size(min = 1, max = 60, message = "{user.firstName.size}")
    private String name;

    @NotNull
    @Min(value = 10, message = "{user.age.min}")
    @Max(value = 20, message = "{user.age.max}")
    private int age;

    public Customer(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
