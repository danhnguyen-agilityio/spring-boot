package com.agility.shopping.cart.utils;

import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.dto.ShoppingCartRequest;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.services.TokenAuthenticationService;
import org.mockito.internal.util.collections.Sets;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * This class is used to fake data
 */
public class FakerUtil {

    /**
     * Fake user with arbitrary username and password
     *
     * @return User
     */
    public static User fakeUser() {
        return new User(generateString(), generateString());
    }

    /**
     * Fake user with given username
     *
     * @param username Username of created user
     * @return User
     */
    public static User fakeUser(String username) {
        return new User(username, generateString());
    }

    /**
     * Fake shopping cart data
     *
     * @return Shopping cart object
     */
    public static ShoppingCart fakeShoppingCart() {
        return fakeShoppingCart(new User());
    }

    /**
     * Fake shopping cart data
     *
     * @return Shopping cart object
     */
    public static ShoppingCart fakeShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(new Random().nextLong());
        shoppingCart.setName(generateString());
        shoppingCart.setUser(user);
        shoppingCart.setStatus(generateString());
        return shoppingCart;
    }

    /**
     * Fake shopping cart request
     *
     * @return Shopping cart request
     */
    public static ShoppingCartRequest fakeShoppingCartRequest() {
        return new ShoppingCartRequest(generateString(), generateString());
    }

    /**
     * Generate member token with random username
     *
     * @return Member token
     */
    public static String generateMemberToken() {
        return generateMemberToken(generateString());
    }

    /**
     * Generate member token with given username
     *
     * @return Member token
     */
    public static String generateMemberToken(String username) {
        Set<String> roles = Sets.newSet(RoleType.MEMBER.getName());
        return TokenAuthenticationService.createToken(username, roles);
    }

    /**
     * Generate admin token with random username
     *
     * @return Admin token
     */
    public static String generateAdminToken() {
        return generateAdminToken(generateString());
    }

    /**
     * Generate admin token with given username
     *
     * @return Admin token
     */
    public static String generateAdminToken(String username) {
        Set<String> roles = Sets.newSet(RoleType.ADMIN.getName());
        return TokenAuthenticationService.createToken(username, roles);
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
