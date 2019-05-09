package com.agility.usermanagement.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuth {

    @NotNull
    String username;

    @NotNull
    String password;
}
