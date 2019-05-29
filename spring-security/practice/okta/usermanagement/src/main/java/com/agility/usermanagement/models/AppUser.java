package com.agility.usermanagement.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    private String id;

    private String email;

    @Transient
    private String password;

    private String firstName;

    private String lastName;

    private List<String> roles;
}
