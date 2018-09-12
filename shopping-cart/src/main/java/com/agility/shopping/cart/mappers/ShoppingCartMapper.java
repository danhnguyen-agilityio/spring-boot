package com.agility.shopping.cart.mappers;

import com.agility.shopping.cart.dto.*;
import com.agility.shopping.cart.models.CartItem;
import com.agility.shopping.cart.models.Product;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.utils.ShoppingCartUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;

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
    @Mapping(source = "shoppingCart", target = "total")
    ShoppingCartResponse toShoppingCartResponse(ShoppingCart shoppingCart);


    /**
     * Calculate total money from shopping cart
     *
     * @param shoppingCart Shopping cart
     * @return Total money from shopping cart
     */
    default Long toTotal(ShoppingCart shoppingCart) {
        return ShoppingCartUtil.calculateTotal(shoppingCart);
    }

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

    /**
     * Convert from cart item to cart item response
     *
     * @param cartItem Cart item
     * @return Cart item response
     */
    @Mappings({
        @Mapping(target = "shoppingCart", ignore = true),
        @Mapping(target = "product", ignore = true)
    })
    CartItemResponse cartItemToCartItemResponse(CartItem cartItem);

    /**
     * Convert from list cart item to list cart item response
     *
     * @param cartItems List cart item
     * @return List cart item response
     */
    Set<CartItemResponse> cartItemToCartItemResponse(Set<CartItem> cartItems);

    /**
     * Convert product to product response
     *
     * @param product
     * @return product response
     */
    ProductResponse productToProductResponse(Product product);
}


