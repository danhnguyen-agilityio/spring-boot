package com.agility.shopping.cart.utils;

import com.agility.shopping.cart.models.ShoppingCart;

/**
 * ShoppingCartUtil class
 */
public class ShoppingCartUtil {

    /**
     * Calculate total from shopping cart
     *
     * @param shoppingCart Shopping cart
     * @return Total money from all product in shopping cart
     */
    public static long calculateTotal(ShoppingCart shoppingCart) {
        if (shoppingCart.getCartItems() == null) return 0;
        return shoppingCart.getCartItems().stream()
            .mapToLong(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getPrice())
            .sum();
    }
}
