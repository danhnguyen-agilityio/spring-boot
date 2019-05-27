package com.agility.spring.models;

import com.agility.spring.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty("customEmail")
    @Email
    private String email;

    @Column(name = "lastName")
    private String lastName;

    public User(String email, String name) {
        this.email = email;
        this.lastName = name;
    }

    public User(UserDTO userDTO) {
        this(userDTO.getEmail(), userDTO.getLastName());
    }
}
