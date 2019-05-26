package com.agility.resourceserver.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserProfileResponse {
    Long id;
    String username;
    String firstName;
    String lastName;
    String address;

    // When user type boolean, lombok generate method isActive(), so we need create getActive() method for using mapper
    boolean active;

    public boolean getActive() {
        return active;
    }
}

