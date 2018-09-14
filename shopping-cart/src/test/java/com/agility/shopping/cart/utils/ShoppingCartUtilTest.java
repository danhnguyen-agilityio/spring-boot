package com.agility.shopping.cart.utils;

import com.agility.shopping.cart.models.CartItem;
import com.agility.shopping.cart.models.ShoppingCart;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import java.util.Set;

import static com.agility.shopping.cart.utils.FakerUtil.fakeCartItemWithQuantityAndPrice;
import static com.agility.shopping.cart.utils.FakerUtil.fakeShoppingCart;
import static org.junit.Assert.*;

/**
 * ShoppingCartUtilTest class
 */
public class ShoppingCartUtilTest {

    /**
     * Test calculate total money from shopping cart
     */
    @Test
    public void testCalculateTotal() {
        // Mock list cart item
        CartItem cartItem1 = fakeCartItemWithQuantityAndPrice(2, 20);
        CartItem cartItem2 = fakeCartItemWithQuantityAndPrice(3, 30);
        Set<CartItem> cartItems = Sets.newSet(cartItem1, cartItem2);

        // Mock shopping cart
        ShoppingCart shoppingCart = fakeShoppingCart();
        shoppingCart.setCartItems(cartItems);

        assertEquals(130, ShoppingCartUtil.calculateTotal(shoppingCart));
    }
}
