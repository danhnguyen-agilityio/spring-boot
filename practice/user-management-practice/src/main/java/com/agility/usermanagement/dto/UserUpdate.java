package com.agility.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * This class is used to catch data from client request
 */
@Getter
@Setter
public class UserUpdate {

    @NotNull
    Long id;

    String firstName;
    String lastName;
    String address;
}
