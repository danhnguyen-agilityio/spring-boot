package com.agility.shopping.cart.dto;

import com.agility.shopping.cart.models.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

/**
 * This class is used to encapsulate response data in value object when app response
 */
@Getter
@Setter
public class CartItemResponse {

    private Long id;
    private ShoppingCartResponse shoppingCart;
    private ProductResponse product;
    private Long quantity;
    private Instant createdAt;
    private Instant updatedAt;
}
