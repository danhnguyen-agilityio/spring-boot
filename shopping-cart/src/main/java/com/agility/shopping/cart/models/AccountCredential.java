package com.agility.shopping.cart.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * AccountCredential class map data body of request login
 */
@Setter
@Getter
@ToString
public class AccountCredential {
    private String username;
    private String password;
}
