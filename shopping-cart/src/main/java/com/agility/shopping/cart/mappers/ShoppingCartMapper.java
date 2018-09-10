package com.agility.shopping.cart.mappers;

import com.agility.shopping.cart.dto.ShoppingCartRequest;
import com.agility.shopping.cart.dto.ShoppingCartResponse;
import com.agility.shopping.cart.dto.UserResponse;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * ShoppingCartMapper interface is used to map between different object models
 * that relate to Product
 */
@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {

    /**
     * Convert from shopping cart request to shopping cart response
     *
     * @param request Shopping cart request
     * @return Shopping cart
     */
    ShoppingCart toShoppingCart(ShoppingCartRequest request);

    /**
     * Convert from shopping cart to shopping cart response
     *
     * @param shoppingCart Shopping cart
     * @return Shopping cart response
     */
    ShoppingCartResponse toShoppingCartResponse(ShoppingCart shoppingCart);

    /**
     * Convert from shopping cart to shopping cart request
     *
     * @param shoppingCart Shopping cart
     * @return Shopping cart request
     */
    // FIXME:: Consider use this method for testing
    ShoppingCartRequest toShoppingCartRequest(ShoppingCart shoppingCart);

    /**
     * Convert from user to user response
     *
     * @param user User need convert
     * @return User response
     */
    UserResponse userToUserResponse(User user);

    /**
     * Convert from shopping cart to shopping cart response
     *
     * @param shoppingCarts List shopping cart
     * @return List shopping cart response
     */
    List<ShoppingCartResponse> toShoppingCartResponse(
        List<ShoppingCart> shoppingCarts);
}


