package com.agility.springbeanscopeannotation;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class Name {
    private String firstName = "Jack";
    private String lastName = "Smith";

    public Name() {
        System.out.println("Create new Name: " + this.toString());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName();
    }
}