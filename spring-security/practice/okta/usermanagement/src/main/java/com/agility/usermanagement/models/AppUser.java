package com.agility.usermanagement.models;

import com.agility.usermanagement.models.converters.RoleListConverter;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    private String id;

    @NotBlank
    private String email;

    @Transient
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Convert(converter = RoleListConverter.class)
    private List<Role> roles = new ArrayList<>();

    private boolean active;
}
