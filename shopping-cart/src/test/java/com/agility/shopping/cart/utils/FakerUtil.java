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
import java.util.concurrent.ThreadLocalRandom;

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
