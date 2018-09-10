package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.dto.CartItemRequest;
import com.agility.shopping.cart.dto.CartItemResponse;
import com.agility.shopping.cart.exceptions.ResourceForbiddenException;
import com.agility.shopping.cart.exceptions.ResourceNotFoundException;
import com.agility.shopping.cart.mappers.CartItemMapper;
import com.agility.shopping.cart.models.CartItem;
import com.agility.shopping.cart.models.Product;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.repositories.CartItemRepository;
import com.agility.shopping.cart.repositories.ProductRepository;
import com.agility.shopping.cart.repositories.ShoppingCartRepository;
import com.agility.shopping.cart.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.agility.shopping.cart.exceptions.CustomError.PRODUCT_NOT_FOUND;
import static com.agility.shopping.cart.exceptions.CustomError.SHOPPING_CART_FORBIDDEN;
import static com.agility.shopping.cart.exceptions.CustomError.SHOPPING_CART_NOT_FOUND;

/**
 * This class implement api that relate to Cart Item data
 */
@RestController
@RequestMapping("/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    /**
     * Create cart item from given request data
     *
     * @param request Cart item request
     * @return Create item response
     * @throws ResourceNotFoundException if shopping car id or product id not exist
     * @throws ResourceForbiddenException if authenticated user not own shopping cart by given id
     */
    @PostMapping
    public CartItemResponse create(@Valid @RequestBody CartItemRequest request) {

        // Get shopping cart by id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(request.getShoppingCartId());

        // Throw Resource not found exception when shopping cart id not exist
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Throw Resource forbidden exception when authenticated user not own this shopping cart
        if (shoppingCartService.isOwnedByAuthenticatedUser(shoppingCart)) {
            throw new ResourceForbiddenException(SHOPPING_CART_FORBIDDEN);
        }

        // Get product by id
        Product product = productRepository.findOne(request.getProductId());

        // Throw Resource not found exception when product id not exist
        if (product == null) {
            throw new ResourceNotFoundException(PRODUCT_NOT_FOUND);
        }

        // Map to cart item
        CartItem cartItem = cartItemMapper.toCartItem(request);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setProduct(product);

        // Create cart item
        cartItem = cartItemRepository.save(cartItem);

        // FIXME:: Check cart item create or update when same shopping cart id and product id
        // Convert cart item to cart item response and return it
        return cartItemMapper.toCartItemResponse(cartItem);
    }
}
