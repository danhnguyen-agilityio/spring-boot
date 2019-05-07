package com.agility.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRegistry {

    @NotNull
    String username;

    @NotNull
    String password;
}
