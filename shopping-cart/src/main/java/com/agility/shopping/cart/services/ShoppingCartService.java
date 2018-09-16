package com.agility.shopping.cart.services;

import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.models.CartItem;
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
            .mapToLong((CartItem cartItem) -> cartItem.getQuantity() * cartItem.getProduct().getPrice())
            .sum();
    }

    /**
     * Check whether or not shopping cart have given shopping cart status
     *
     * @param shoppingCart Shopping cart need check
     * @param status       Status shopping cart
     * @return true if shopping cart have given status, else return false
     */
    public boolean haveStatus(ShoppingCart shoppingCart, ShoppingCartStatus status) {
        return status.getName().equals(shoppingCart.getStatus());
    }
}
