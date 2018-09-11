package com.agility.mapstruct.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryContact {
    private String name;
    private String phone;
    private String email;
}
