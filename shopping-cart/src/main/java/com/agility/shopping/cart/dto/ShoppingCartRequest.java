package com.agility.shopping.cart.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Size(min = 4, max = 30)
    private String name;

    @NotNull
    @Size(min = 10, max = 30)
    private String description;
}
