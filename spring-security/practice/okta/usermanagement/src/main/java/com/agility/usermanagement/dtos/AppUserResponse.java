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
}
