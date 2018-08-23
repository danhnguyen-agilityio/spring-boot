package com.agility.spring.dto;

import com.agility.spring.models.User;

import javax.validation.constraints.Email;

public class UserDTO {
    private long id;

    @Email
    private String email;

    private String lastName;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Email: " + email + ", Name: " + lastName;
    }
}
