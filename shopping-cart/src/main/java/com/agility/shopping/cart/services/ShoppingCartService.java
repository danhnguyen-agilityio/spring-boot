package com.agility.shopping.cart.services;

import com.agility.shopping.cart.models.ShoppingCart;
import org.springframework.stereotype.Service;

/**
 * ShoppingCartService class
 */
@Service
public class ShoppingCartService {

    /**
     * Calculate total from shopping cart
     *
     * @param shoppingCart Shopping cart
     * @return Total money from all product in shopping cart
     */
    public long calculateTotal(ShoppingCart shoppingCart) {
        if (shoppingCart.getCartItems() == null) return 0;
        return shoppingCart.getCartItems().stream()
            .mapToLong(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getPrice())
            .sum();
    }
}
