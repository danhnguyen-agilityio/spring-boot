package com.agility.shopping.cart.models;

import lombok.Getter;
import lombok.Setter;

/**
 * AccountCredential class map data body of request login
 */
@Setter
@Getter
public class AccountCredential {
    private String username;
    private String password;
}
