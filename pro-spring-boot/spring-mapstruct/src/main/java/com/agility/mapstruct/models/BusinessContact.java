package com.agility.mapstruct.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessContact {
    private String firstName;
    private String lastName;
    private String businessPhone;
    private String businessEmail;
    private String businessCountry;
}
