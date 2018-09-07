package com.agility.shopping.cart.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to encapsulate response data when app response
 */
@Getter
@Setter
public class ShoppingCartResponse {

    private Long id;
    private String name;
    private String description;
    private UserResponse user;
    private String status;
}
