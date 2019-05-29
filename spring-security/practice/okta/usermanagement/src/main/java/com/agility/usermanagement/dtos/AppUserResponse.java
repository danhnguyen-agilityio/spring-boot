package com.agility.usermanagement.dtos;

import com.agility.usermanagement.models.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponse {

    private String id;

    private String email;

    private String firstName;

    private String lastName;

    private List<Role> roles;

    private boolean active;

    // When user type boolean, lombok generate method isActive(), so we need create getActive() method for using mapper
    public boolean getActive() {
        return active;
    }
}
