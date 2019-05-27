package com.agility.usermanagement.dto;

import lombok.*;

import java.util.List;

/**
 * Encapsulate data in object UserResponse to returned to client
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    Long id;
    String username;
    String firstName;
    String lastName;
    String address;

    // When user type boolean, lombok generate method isActive(), so we need create getActive() method for using mapper
    boolean active;
    List<String> roles;

    public boolean getActive() {
        return active;
    }
}
