package com.agility.shopping.cart.services;

import com.agility.shopping.cart.models.CartItem;
import com.agility.shopping.cart.models.ShoppingCart;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * ShoppingCartServiceTest class
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartServiceTest {

    @Autowired
    private FakerService fakerService;

    @Autowired ShoppingCartService shoppingCartService;

    /**
     * Test calculate total money from shopping cart
     */
    @Test
    public void testCalculateTotal() {
        // Mock list cart item
        CartItem cartItem1 = fakerService.fakeCartItemWithQuantityAndPrice(2, 20);
        CartItem cartItem2 = fakerService.fakeCartItemWithQuantityAndPrice(3, 30);
        Set<CartItem> cartItems = Sets.newSet(cartItem1, cartItem2);

        // Mock shopping cart
        ShoppingCart shoppingCart = fakerService.fakeShoppingCart();
        shoppingCart.setCartItems(cartItems);

        Assert.assertEquals(130, shoppingCartService.calculateTotal(shoppingCart));
    }
}
