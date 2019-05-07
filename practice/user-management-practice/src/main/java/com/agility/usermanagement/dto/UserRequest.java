package com.agility.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRequest {

    @NotNull
    String username;

    @NotNull
    String password;
}
