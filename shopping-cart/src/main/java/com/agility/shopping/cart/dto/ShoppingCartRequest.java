package com.agility.shopping.cart.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * This class is used to encapsulate body data in object value when user request
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartRequest {

    @NotNull
    private String name;
}
