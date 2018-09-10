package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.constants.SecurityConstants;
import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.dto.CartItemRequest;
import com.agility.shopping.cart.dto.CartItemResponse;
import com.agility.shopping.cart.exceptions.BadRequestException;
import com.agility.shopping.cart.exceptions.ResourceForbiddenException;
import com.agility.shopping.cart.exceptions.ResourceNotFoundException;
import com.agility.shopping.cart.mappers.CartItemMapper;
import com.agility.shopping.cart.models.CartItem;
import com.agility.shopping.cart.models.Product;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.repositories.CartItemRepository;
import com.agility.shopping.cart.repositories.ProductRepository;
import com.agility.shopping.cart.repositories.ShoppingCartRepository;
import com.agility.shopping.cart.services.TokenAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.agility.shopping.cart.exceptions.CustomError.PRODUCT_NOT_FOUND;
import static com.agility.shopping.cart.exceptions.CustomError.SHOPPING_CART_DONE;
import static com.agility.shopping.cart.exceptions.CustomError.SHOPPING_CART_NOT_FOUND;

/**
 * This class implement api that relate to Cart Item data
 */
@RestController
@RequestMapping("/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    /**
     * Create cart item from given request data
     *
     * @param cartItemRequest Cart item request
     * @return Create item response
     * @throws ResourceNotFoundException  if shopping car id or product id not exist
     * @throws ResourceForbiddenException if authenticated user not own shopping cart by given id
     */
    @PostMapping
    public CartItemResponse create(@Valid @RequestBody CartItemRequest cartItemRequest,
                                   HttpServletRequest request) {

        // Get user id from request
        Long userId = TokenAuthenticationService.getUserId(getToken(request));

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), userId);

        // Throw Resource not found exception when no shopping cart by given shopping cart id and user id
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Throw bad request exception when no shopping cart is already done
        if (isShoppingCartDone(shoppingCart)) {
            throw new BadRequestException(SHOPPING_CART_DONE);
        }

        // Get product by id
        Product product = productRepository.findOne(cartItemRequest.getProductId());

        // Throw Resource not found exception when product id not exist
        if (product == null) {
            throw new ResourceNotFoundException(PRODUCT_NOT_FOUND);
        }

        // Get cart item by shopping cart id and product id
        CartItem cartItem = cartItemRepository.findOne(cartItemRequest.getShoppingCartId(), cartItemRequest.getProductId());

        if (cartItem == null) {
            // Map cart item request to cart item
            cartItemMapper.toCartItem(cartItemRequest);
            cartItem.setShoppingCart(shoppingCart);
            cartItem.setProduct(product);
        } else {
            // Plus more quantity to cart item already exist
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
        }

        // Create or update cart item
        cartItem = cartItemRepository.save(cartItem);

        // Convert cart item to cart item response and return it
        return cartItemMapper.toCartItemResponse(cartItem);
    }

    /**
     * Get token from request
     *
     * @param request Request from user
     * @return Token string
     */
    private String getToken(HttpServletRequest request) {
        return request.getHeader(SecurityConstants.HEADER_STRING);
    }

    /**
     * Check whether or not shopping cart is done
     *
     * @param shoppingCart Shopping cart need check
     * @return true if shopping cart is done, else return false
     */
    private boolean isShoppingCartDone(ShoppingCart shoppingCart) {
        return ShoppingCartStatus.DONE.getName().equals(shoppingCart.getStatus());
    }
}
