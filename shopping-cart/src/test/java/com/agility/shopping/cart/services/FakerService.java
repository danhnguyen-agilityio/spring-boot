package com.agility.shopping.cart.services;

import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.dto.CartItemRequest;
import com.agility.shopping.cart.dto.CartItemUpdate;
import com.agility.shopping.cart.dto.ProductRequest;
import com.agility.shopping.cart.dto.ShoppingCartRequest;
import com.agility.shopping.cart.models.*;
import com.github.javafaker.Faker;
import org.mockito.internal.util.collections.Sets;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is used to fake data
 */
@Service
public class FakerService {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    /**
     * Fake user with given roles
     *
     * @param roleTypes List role type of user
     * @return User with given roles
     */
    public User fakeUser(List<RoleType> roleTypes) {
        Set<Role> roles = roleTypes.stream()
            .map(roleType -> new Role(roleType.getName()))
            .collect(Collectors.toSet());
        return User.builder()
            .id(faker.number().randomNumber())
            .username(faker.name().name())
            .password(faker.name().name())
            .roles(roles)
            .build();
    }

    /**
     * Fake user with given role
     *
     * @param roleType Role type of user
     * @return User with given role
     */
    public User fakeUser(RoleType roleType) {
        return fakeUser(Arrays.asList(roleType));
    }

    /**
     * Fake user
     *
     * @return User
     */
    public User fakeUser() {
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
    public User fakeAdminUser() {
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
    public User fakeMemberUser() {
        User user = fakeUser();
        Set<Role> roles = Sets.newSet(new Role(RoleType.MEMBER.getName()));
        user.setRoles(roles);
        return user;
    }

    /**
     * Fake product
     *
     * @return Product
     */
    public Product fakeProduct() {
        return Product.builder()
            .id(faker.number().randomNumber())
            .name(faker.name().name())
            .price(faker.number().randomNumber())
            .build();
    }

    /**
     * Fake list product with given size
     *
     * @param size Size list product
     * @return List product with given size
     */
    public List<Product> fakeListProduct(int size) {
        return Stream.generate(() -> fakeProduct()).limit(size).collect(Collectors.toList());
    }

    /**
     * Fake product request
     *
     * @return Product request
     */
    public ProductRequest fakeProductRequest() {
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
    public ShoppingCart fakeShoppingCart(ShoppingCartStatus shoppingCartStatus) {
        ShoppingCart shoppingCart = fakeShoppingCart();
        shoppingCart.setStatus(shoppingCartStatus.getName());
        return shoppingCart;
    }

    /**
     * Fake shopping cart data
     *
     * @return Shopping cart object
     */
    public ShoppingCart fakeShoppingCart() {
        return ShoppingCart.builder()
            .id(faker.number().randomNumber())
            .name(faker.lorem().characters(4, 30))
            .description(faker.lorem().characters(10, 30))
            .status(randomEnum(ShoppingCartStatus.class).getName())
            .user(fakeUser(RoleType.MEMBER))
            .build();
    }

    /**
     * Fake shopping cart data
     *
     * @return Shopping cart object
     */
    public ShoppingCart fakeShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(faker.number().randomNumber());
        shoppingCart.setName(faker.lorem().characters(4, 30));
        shoppingCart.setDescription(faker.lorem().characters(10, 30));
        shoppingCart.setUser(user);
        shoppingCart.setStatus(randomEnum(ShoppingCartStatus.class).getName());
        return shoppingCart;
    }

    /**
     * Fake list shopping cart with given size
     *
     * @param size Size list shopping cart
     * @return List shopping cart with given size
     */
    public List<ShoppingCart> fakeListShoppingCart(int size) {
        return Stream.generate(() -> fakeShoppingCart()).limit(size).collect(Collectors.toList());
    }

    /**
     * Fake shopping cart request
     *
     * @return Shopping cart request
     */
    public ShoppingCartRequest fakeShoppingCartRequest() {
        return new ShoppingCartRequest(faker.lorem().characters(4, 30), faker.lorem().characters(10, 30));
    }

    /**
     * Fake cart item
     *
     * @return Cart item
     */
    public CartItem fakeCartItem() {
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
    public CartItem fakeCartItemWithQuantityAndPrice(long quantity, long price) {
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
    public CartItemRequest fakeCartItemRequest() {
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
    public CartItemUpdate fakeCartItemUpdate() {
        return CartItemUpdate.builder()
            .shoppingCartId(faker.number().randomNumber())
            .quantity(faker.number().numberBetween(1, Long.MAX_VALUE))
            .build();
    }

    /**
     * Fake list cart item with given size
     *
     * @param size Size list cart item
     * @return List cart item with given size
     */
    public List<CartItem> fakeListCartItem(int size) {
        return Stream.generate(() -> fakeCartItem()).limit(size).collect(Collectors.toList());
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
