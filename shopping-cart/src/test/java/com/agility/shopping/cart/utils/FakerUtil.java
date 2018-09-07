package com.agility.shopping.cart.utils;

import com.agility.shopping.cart.models.ShoppingCart;

import java.util.Random;
import java.util.UUID;

/**
 * This class is used to fake data
 */
public class FakerUtil {

    /**
     * Fake shopping cart data
     *
     * @return Shopping cart object
     */
    public static ShoppingCart fakeShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(new Random().nextLong());
        shoppingCart.setName(generateString());
        shoppingCart.setStatus(generateString());
        return shoppingCart;
    }

    /**
     * Generate random string
     *
     * @return Random string
     */
    public static String generateString() {
        return UUID.randomUUID().toString();
    }


}
