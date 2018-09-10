package com.agility.shopping.cart.mappers;

import com.agility.shopping.cart.dto.*;
import com.agility.shopping.cart.models.CartItem;
import com.agility.shopping.cart.models.Product;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import org.mapstruct.Mapper;

/**
 * This interface is used to map between different object model
 */
@Mapper(componentModel = "spring")
public interface CartItemMapper {

    /**
     * Convert from cart item request to cart item
     *
     * @param request Cart item request
     * @return Cart item
     */
    CartItem toCartItem(CartItemRequest request);

    /**
     * Convert from cart item to cart item response
     *
     * @param cartItem Cart item
     * @return Cart item response
     */
    CartItemResponse toCartItemResponse(CartItem cartItem);

    /**
     * Convert from shopping cart to shopping cart response
     *
     * @param shoppingCart Shopping cart
     * @return Shopping cart response
     */
    ShoppingCartResponse toShoppingCartResponse(ShoppingCart shoppingCart);

    /**
     * Convert from product to product response
     *
     * @param product Product
     * @return Product response
     */
    ProductResponse toProductResponse(Product product);

    /**
     * Convert from user to user response
     *
     * @param user User need convert
     * @return User response
     */
    UserResponse userToUserResponse(User user);
}
