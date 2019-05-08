package com.agility.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

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
    private String role;
}
