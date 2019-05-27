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
public class CartItemRequest {

    @NotNull
    private Long shoppingCartId;

    @NotNull
    private Long productId;

    @Min(1)
    private Long quantity;
}
