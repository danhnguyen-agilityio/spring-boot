package com.agility.shopping.cart.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * This class is used to encapsulate body data in a value object when user request
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemUpdate {

    @NotNull
    private Long shoppingCartId;

    @NotNull
    @Min(1)
    private Long quantity;
}
