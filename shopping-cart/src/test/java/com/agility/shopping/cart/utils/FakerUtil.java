package com.agility.shopping.cart.utils;

import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.dto.CartItemRequest;
import com.agility.shopping.cart.dto.CartItemUpdate;
import com.agility.shopping.cart.dto.ShoppingCartRequest;
import com.agility.shopping.cart.models.*;
import com.agility.shopping.cart.services.TokenAuthenticationService;
import org.mockito.internal.util.collections.Sets;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is used to fake data
 */
public class FakerUtil {

    /**
     * Fake user
     *
     * @return User
     */
    public static User fakeUser() {
        User user = new User();
        user.setId(generateLongNumber());
        user.setUsername(generateString());
        user.setPassword(generateString());
        return user;
    }

    /**
     * Fake admin user
     *
     * @return Admin user
     */
    public static User fakeAdminUser() {
        User user = fakeUser();
        Set<Role> roles = Sets.newSet(new Role(RoleType.ADMIN.getName()));
        user.setRoles(roles);
        return user;
    }

    /**
     * Fake member user
     *
     * @return Member user
     */
    public static User fakeMemberUser() {
        User user = fakeUser();
        Set<Role> roles = Sets.newSet(new Role(RoleType.MEMBER.getName()));
        user.setRoles(roles);
        return user;
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
     * Fake product
     *
     * @return Product
     */
    public static Product fakeProduct() {
        Product product = new Product();
        product.setId(generateLongNumber());
        product.setName(generateString());
        product.setPrice(generateLongNumber());
        return product;
    }

    /**
     * Fake shopping cart data with
     *
     * @param shoppingCartStatus
     * @return
     */
    public static ShoppingCart fakeShoppingCart(ShoppingCartStatus shoppingCartStatus) {
        ShoppingCart shoppingCart = fakeShoppingCart();
        shoppingCart.setStatus(shoppingCartStatus.getName());
        return shoppingCart;
    }

    /**
     * Fake shopping cart data
     *
     * @return Shopping cart object
     */
    public static ShoppingCart fakeShoppingCart() {
        return fakeShoppingCart(fakeUser());
    }

    /**
     * Fake shopping cart data
     *
     * @return Shopping cart object
     */
    public static ShoppingCart fakeShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(new Random().nextLong());
        shoppingCart.setName(generateString(4, 30));
        shoppingCart.setDescription(generateString(10, 30));
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
        return new ShoppingCartRequest(generateString(4, 30), generateString(10, 30));
    }

    /**
     * Fake cart item
     *
     * @return Cart item
     */
    public static CartItem fakeCartItem() {
        CartItem cartItem = new CartItem();
        cartItem.setId(generateLongNumber());
        cartItem.setQuantity(generateLongNumber(1));
        cartItem.setShoppingCart(fakeShoppingCart());
        cartItem.setProduct(fakeProduct());
        return cartItem;
    }

    /**
     * Fake cart item with quantity and price product
     *
     * @param quantity Quantity of product
     * @param price    Price of product
     * @return Cart item
     */
    public static CartItem fakeCartItemWithQuantityAndPrice(long quantity, long price) {
        CartItem cartItem = fakeCartItem();
        cartItem.setQuantity(quantity);
        cartItem.getProduct().setPrice(price);
        return cartItem;
    }

    /**
     * Fake cart item request
     *
     * @return Cart item request
     */
    public static CartItemRequest fakeCartItemRequest() {
        CartItemRequest cartItemRequest = new CartItemRequest();
        cartItemRequest.setShoppingCartId(generateLongNumber());
        cartItemRequest.setProductId(generateLongNumber());
        cartItemRequest.setQuantity(generateLongNumber(1));
        return cartItemRequest;
    }

    /**
     * Fake cart item update
     *
     * @return Cart item update
     */
    public static CartItemUpdate fakeCartItemUpdate() {
        CartItemUpdate cartItemUpdate = new CartItemUpdate();
        cartItemUpdate.setShoppingCartId(generateLongNumber());
        cartItemUpdate.setQuantity(generateLongNumber(1));
        return cartItemUpdate;
    }

    /**
     * Fake list 3 element cart item
     *
     * @return List cart item
     */
    public static List<CartItem> fakeListCartItem() {
        return Arrays.asList(fakeCartItem(), fakeCartItem(), fakeCartItem());
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
     * Generate long number with given min value
     *
     * @param min Min value of created long number
     * @return Long number
     */
    public static long generateLongNumber(long min) {
        return generateLongNumber(min, Long.MAX_VALUE);
    }

    /**
     * Generate long number with given min and max value
     *
     * @param min Min value of created long number
     * @param max Max value of created long number
     * @return Long number
     */
    public static long generateLongNumber(long min, long max) {
        return ThreadLocalRandom.current().nextLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * Generate long number
     *
     * @return Long number
     */
    public static Long generateLongNumber() {
        return new Random().nextLong();
    }

    /**
     * Generate random string with given length
     *
     * @param size Size of created string
     * @return Random string
     */
    public static String generateString(int size) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String subset = "0123456789abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < size; i++) {
            int index = random.nextInt(subset.length());
            char c = subset.charAt(index);
            sb.append(c);
        }

        return sb.toString();
    }

    /**
     * Generate random string with arbitrary length
     *
     * @return Random string
     */
    public static String generateString() {
        return generateString(1, 100);
    }

    /**
     * Generate random string with size random from minSize to maxSize
     *
     * @param minSize Max size of string
     * @param maxSize Min size of string
     * @return Random String
     */
    public static String generateString(int minSize, int maxSize) {
        return generateString(generateInteger(minSize, maxSize));
    }

    /**
     * Return random integer number
     *
     * @return Integer number
     */
    public static int generateInteger() {
        return ThreadLocalRandom.current().nextInt();
    }

    /**
     * Return integer number random from min to max
     *
     * @param min Min number
     * @param max Max number
     * @return Integer number
     */
    public static int generateInteger(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }


}
