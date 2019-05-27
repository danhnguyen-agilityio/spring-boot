package com.agility.resourceserver.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
