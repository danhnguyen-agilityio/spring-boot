package com.agility.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Encapsulate data in object UserResponse to returned to client
 */
@Getter
@Setter
public class UserResponse {
    Long id;
    String username;
    String firstName;
    String lastName;
    String address;
    Boolean active;
    List<String> roles;
}
