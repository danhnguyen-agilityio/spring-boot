package com.agility.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

/**
 * This class is used to catch data from client request
 */
@Getter
@Setter
public class UserUpdate {
    private String firstName;
    private String lastName;
    private String address;
    private boolean active;

    @Pattern(regexp = "(USER|ADMIN|MANAGER)")
    private String role;
}
