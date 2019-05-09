package com.agility.usermanagement.dto;

import lombok.*;

import javax.validation.constraints.Pattern;

/**
 * This class is used to catch data from client request
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdate {
    private String firstName;
    private String lastName;
    private String address;
    private boolean active;

    @Pattern(regexp = "(USER|ADMIN|MANAGER)")
    private String role;
}
