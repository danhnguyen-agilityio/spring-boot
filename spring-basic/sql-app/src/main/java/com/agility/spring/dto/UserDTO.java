package com.agility.spring.dto;

import com.agility.spring.models.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    @Email(message = "{user.email}")
    private String email;

    @NotNull
    @Size(min = 10, max = 30, message = "{user.lastName.size}")
    private String lastName;

    public UserDTO() {
    }

    public UserDTO(String email, String lastName) {
        this.email = email;
        this.lastName = lastName;
    }

    public UserDTO(User user) {
        this.lastName = user.getLastName();
        this.email = user.getEmail();
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
