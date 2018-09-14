package com.agility.shopping.cart.utils;

import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.dto.CartItemRequest;
import com.agility.shopping.cart.dto.CartItemUpdate;
import com.agility.shopping.cart.dto.ProductRequest;
import com.agility.shopping.cart.dto.ShoppingCartRequest;
import com.agility.shopping.cart.models.*;
import com.agility.shopping.cart.services.TokenAuthenticationService;
import com.github.javafaker.Faker;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * This class is used to fake data
 */
public class FakerUtil {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    /**
     * Fake user
     *
     * @return User
     */
    public static User fakeUser() {
        User user = new User();
        user.setId(faker.number().randomNumber());
        user.setUsername(faker.name().name());
        user.setPassword(faker.name().name());
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
        return new User(username, faker.internet().password());
    }

    /**
     * Fake product
     *
     * @return Product
     */
    public static Product fakeProduct() {
        Product product = new Product();
        product.setId(faker.number().randomNumber());
        product.setName(faker.name().name());
        product.setPrice(faker.number().randomNumber());
        return product;
    }

    /**
     * Fake product request
     *
     * @return Product request
     */
    public static ProductRequest fakeProductRequest() {
        ProductRequest request = new ProductRequest();
        request.setName(faker.name().name());
        request.setPrice(faker.number().randomNumber());
        request.setUrl(faker.internet().url());
        return request;
    }

    /**
     * Fake shopping cart data with
     *
     * @param shoppingCartStatus Shopping cart status
     * @return Shopping cart with given status
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
        shoppingCart.setId(faker.number().randomNumber());
        shoppingCart.setName(faker.lorem().characters(4, 30));
        shoppingCart.setDescription(faker.lorem().characters(10, 30));
        shoppingCart.setUser(user);
        shoppingCart.setStatus(randomEnum(ShoppingCartStatus.class).getName());
        return shoppingCart;
    }

    /**
     * Fake shopping cart request
     *
     * @return Shopping cart request
     */
    public static ShoppingCartRequest fakeShoppingCartRequest() {
        return new ShoppingCartRequest(faker.lorem().characters(4, 30), faker.lorem().characters(10, 30));
    }

    /**
     * Fake cart item
     *
     * @return Cart item
     */
    public static CartItem fakeCartItem() {
        CartItem cartItem = new CartItem();
        cartItem.setId(faker.number().randomNumber());
        cartItem.setQuantity(faker.number().numberBetween(1, Long.MAX_VALUE));
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
        cartItemRequest.setShoppingCartId(faker.number().randomNumber());
        cartItemRequest.setProductId(faker.number().randomNumber());
        cartItemRequest.setQuantity(faker.number().numberBetween(1, Long.MAX_VALUE));
        return cartItemRequest;
    }

    /**
     * Fake cart item update
     *
     * @return Cart item update
     */
    public static CartItemUpdate fakeCartItemUpdate() {
        CartItemUpdate cartItemUpdate = new CartItemUpdate();
        cartItemUpdate.setShoppingCartId(faker.number().randomNumber());
        cartItemUpdate.setQuantity(faker.number().numberBetween(1, Long.MAX_VALUE));
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
     * Random enum value from give enum
     *
     * @param clazz Class with generate type Enum
     * @param <T>   Generate type
     * @return Enum value
     */
    private static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

}
