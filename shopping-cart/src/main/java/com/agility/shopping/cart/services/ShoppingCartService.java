package com.agility.shopping.cart.services;

import com.agility.shopping.cart.models.ShoppingCart;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * ShoppingCartService class implement business that relate shopping cart data
 */
@Service
public class ShoppingCartService {

    /**
     * Check whether or not authenticated user own given shopping cart
     *
     * @param shoppingCart Shopping cart need check
     * @return true if authenticated user own shopping cart, other false
     */
    // FIXME:: Consider this method in here or UserService
    public boolean isOwnedByAuthenticatedUser(ShoppingCart shoppingCart) {
        // Get authenticated username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return username.equals(shoppingCart.getUser().getUsername());
    }
}
