package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.constants.MessageConstant;
import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.dto.CartItemRequest;
import com.agility.shopping.cart.dto.CartItemResponse;
import com.agility.shopping.cart.dto.CartItemUpdate;
import com.agility.shopping.cart.exceptions.BadRequestException;
import com.agility.shopping.cart.exceptions.ResourceForbiddenException;
import com.agility.shopping.cart.exceptions.ResourceNotFoundException;
import com.agility.shopping.cart.models.CartItem;
import com.agility.shopping.cart.models.Product;
import com.agility.shopping.cart.models.ShoppingCart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.net.URI;
import java.util.List;

import static com.agility.shopping.cart.exceptions.CustomError.*;
import static org.springframework.http.ResponseEntity.*;

/**
 * This class implement api that relate to Cart Item data
 */
@RestController
@RequestMapping("/cart-items")
// FIXME:: Consider apply RequiredArgsConstructor
@Slf4j
public class CartItemController extends BaseController {


    /**
     * Create cart item from given request data
     *
     * @param cartItemRequest Cart item request
     * @return Create item response
     * @throws ResourceNotFoundException  if shopping car id of authenticated user or product id not exist
     * @throws BadRequestException        if shopping cart already DONE
     * @throws ResourceForbiddenException if authenticated user not own shopping cart by given id
     */
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody CartItemRequest cartItemRequest,
                                   HttpServletRequest request) {
        log.debug("POST /cart-items, body = {}", cartItemRequest);
        // Get user id from request
        Long userId = jwtTokenService.getUserId(request);

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), userId);

        // Throw Resource not found exception when no shopping cart by given shopping cart id and user id
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Throw bad request exception when shopping cart is already done
        if (shoppingCartService.haveStatus(shoppingCart, ShoppingCartStatus.DONE)) {
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
            cartItem = cartItemMapper.toCartItem(cartItemRequest);
            cartItem.setShoppingCart(shoppingCart);
            cartItem.setProduct(product);
        } else {
            // Plus more quantity to cart item already exist
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
        }

        // Create or update cart item
        cartItem.getShoppingCart().setStatus(ShoppingCartStatus.IN_PROGRESS.getName());
        cartItem = cartItemRepository.save(cartItem);

        URI createdItemUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(cartItem.getId())
            .toUri();
        return created(createdItemUri ).build();
    }

    /**
     * Find all cart item by shopping cart id
     *
     * @param shoppingCartId Shopping cart id
     * @param request        Request from user
     * @return List cart item response
     * @throws ResourceNotFoundException if shopping cart with given id of authenticated user not exist
     */
    @GetMapping
    public ResponseEntity<List<CartItemResponse>> findAll(@RequestParam(value = "shoppingCartId") long shoppingCartId,
                                          HttpServletRequest request) {

        // Get user id from request
        Long userId = jwtTokenService.getUserId(request);

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(shoppingCartId, userId);

        // Throw Resource not found exception when no shopping cart by given shopping cart id and user id
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Get list cart item by shopping cart id
        List<CartItem> cartItems = cartItemRepository.findAllByShoppingCartId(shoppingCartId);

        // Convert to list cart item response and return it
        return ok().body(cartItemMapper.toCartItemResponse(cartItems));
    }

    /**
     * Find one cart item in given shopping cart
     *
     * @param cartItemId     Cart item id
     * @param shoppingCartId Shopping cart id
     * @param request        Request from user
     * @return Cart item response
     * @throws ResourceNotFoundException if shopping cart or cart item not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponse> findOne(@PathVariable("id") long cartItemId,
                                    @RequestParam(value = "shoppingCartId") long shoppingCartId,
                                    HttpServletRequest request) {
        // Get user id from request
        Long userId = jwtTokenService.getUserId(request);

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(shoppingCartId, userId);

        // Throw Resource not found exception when no shopping cart by given shopping cart id and user id
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Get cart item by given cart item id and shopping cart id
        CartItem cartItem = cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItemId, shoppingCartId);

        // Throw resource not found exception when cart item not exist
        if (cartItem == null) {
            throw new ResourceNotFoundException(CART_ITEM_NOT_FOUND);
        }

        return new ResponseEntity<>(cartItemMapper.toCartItemResponse(cartItem), HttpStatus.OK);
    }

    /**
     * Update cart item in given shopping cart
     *
     * @param cartItemId     Cart item id
     * @param cartItemUpdate Cart item update
     * @param request        Request from user
     * @return Updated cart item response
     * @throws ResourceNotFoundException if shopping cart or cart item not exist
     * @throws BadRequestException       if shopping cart already DONE
     */
    @PutMapping("/{id}")
    // FIXME:: Should return content or no
    public ResponseEntity<CartItemResponse> update(@PathVariable("id") long cartItemId,
                                   @Valid @RequestBody CartItemUpdate cartItemUpdate,
                                   HttpServletRequest request) {
        // Get user id from request
        Long userId = jwtTokenService.getUserId(request);

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), userId);

        // Throw Resource not found exception when no shopping cart by given shopping cart id and user id
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Throw bad request exception when shopping cart is already done
        if (shoppingCartService.haveStatus(shoppingCart, ShoppingCartStatus.DONE)) {
            throw new BadRequestException(SHOPPING_CART_DONE);
        }

        // Get cart item by given cart item id and shopping cart id
        CartItem cartItem = cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItemId,
            cartItemUpdate.getShoppingCartId());

        // Throw resource not found exception when cart item not exist
        if (cartItem == null) {
            throw new ResourceNotFoundException(CART_ITEM_NOT_FOUND);
        }

        // Set new info to cartItem and save to database
        cartItem.setQuantity(cartItemUpdate.getQuantity());
        cartItem = cartItemRepository.save(cartItem);

        return status(HttpStatus.OK).body(cartItemMapper.toCartItemResponse(cartItem));
    }

    /**
     * Delete cart item in given shopping cart
     *
     * @param cartItemId     Cart item id
     * @param shoppingCartId Shopping cart id
     * @param request        Request from user
     * @return Message success
     * @throws ResourceNotFoundException if shopping cart or cart item not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long cartItemId,
                         @RequestParam long shoppingCartId,
                         HttpServletRequest request) {
        log.debug("Delete /cart-items/{}?shoppingCartId={}", cartItemId, shoppingCartId);

        // Get user id from request
        long userId = jwtTokenService.getUserId(request);

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(shoppingCartId, userId);

        // Throw Resource not found exception when no shopping cart by given shopping cart id and user id
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Get cart item by given cart item id and shopping cart id
        CartItem cartItem = cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItemId, shoppingCartId);

        // Throw resource not found exception when cart item not exist
        if (cartItem == null) {
            throw new ResourceNotFoundException(CART_ITEM_NOT_FOUND);
        }

        // Remove cart item
        shoppingCart.getCartItems().remove(cartItem);

        // Change status if size cart item equal 0
        if (shoppingCart.getCartItems().size() == 0) {
            shoppingCart.setStatus(ShoppingCartStatus.EMPTY.name());
        }

        // Save shopping cart
        shoppingCartRepository.save(shoppingCart);

        return noContent().build();
    }
}
