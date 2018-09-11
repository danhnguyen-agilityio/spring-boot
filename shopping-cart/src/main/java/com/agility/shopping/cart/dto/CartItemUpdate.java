package com.agility.shopping.cart.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * This class is used to encapsulate body data in a value object when user request
 */
@Getter
@Setter
public class CartItemUpdate {

    @NotNull
    private long shoppingCartId;

    @NotNull
    @Min(1)
    private long quantity;
}
