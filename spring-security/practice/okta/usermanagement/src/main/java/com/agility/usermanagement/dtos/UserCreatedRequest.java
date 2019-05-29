package com.agility.usermanagement.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreatedRequest {

    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}",
        message = "Password must at least one lower case, one upper case, one number and min length is 8")
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
