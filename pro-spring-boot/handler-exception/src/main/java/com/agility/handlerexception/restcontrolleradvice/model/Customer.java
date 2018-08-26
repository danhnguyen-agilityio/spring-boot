package com.agility.handlerexception.restcontrolleradvice.model;

import com.agility.handlerexception.restcontrolleradvice.customvalidations.Adult;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class Customer {

    @NotNull(message = "{user.firstName.notNull}")
    @Size(min = 1, max = 60, message = "{user.firstName.size}")
    private String name;

    @NotNull
    @Min(value = 10, message = "{user.age.min}")
    @Max(value = 20, message = "{user.age.max}")
    private int age;

    @NotNull
    @Past
    @Adult(message = "{user.dateOfBirth.adult}")
    private LocalDate dateOfBirth;

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
