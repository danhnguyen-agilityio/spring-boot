package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.constants.SecurityConstants;
import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.dto.CartItemRequest;
import com.agility.shopping.cart.dto.CartItemResponse;
import com.agility.shopping.cart.dto.CartItemUpdate;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

import static com.agility.shopping.cart.exceptions.CustomError.*;

/**
 * This class implement api that relate to Cart Item data
 */
@RestController
@RequestMapping("/cart-items")
// FIXME:: Consider apply RequiredArgsConstructor
@Slf4j
public class CartItemController {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemMapper cartItemMapper;

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
    public CartItemResponse create(@Valid @RequestBody CartItemRequest cartItemRequest,
                                   HttpServletRequest request) {
        log.debug("POST /cart-items, body = {}", cartItemRequest);
        // Get user id from request
        Long userId = TokenAuthenticationService.getUserId(getToken(request));

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), userId);

        // Throw Resource not found exception when no shopping cart by given shopping cart id and user id
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Throw bad request exception when shopping cart is already done
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

        // Convert cart item to cart item response and return it
        return cartItemMapper.toCartItemResponse(cartItem);
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
    public List<CartItemResponse> findAll(@RequestParam(value = "shoppingCartId") long shoppingCartId,
                                          HttpServletRequest request) {

        // Get user id from request
        Long userId = TokenAuthenticationService.getUserId(getToken(request));

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(shoppingCartId, userId);

        // Throw Resource not found exception when no shopping cart by given shopping cart id and user id
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Get list cart item by shopping cart id
        List<CartItem> cartItems = cartItemRepository.findAllByShoppingCartId(shoppingCartId);

        // Convert to list cart item response and return it
        return cartItemMapper.toCartItemResponse(cartItems);
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
    public CartItemResponse findOne(@PathVariable("id") long cartItemId,
                                    @RequestParam(value = "shoppingCartId") long shoppingCartId,
                                    HttpServletRequest request) {
        // Get user id from request
        Long userId = TokenAuthenticationService.getUserId(getToken(request));

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

        return cartItemMapper.toCartItemResponse(cartItem);
    }

    /**
     * Update cart item in given shopping cart
     *
     * @param cartItemId     Cart item id
     * @param cartItemUpdate Cart item update
     * @param request        Request from user
     * @return Updated cart item response
     * @throws ResourceNotFoundException if shopping cart or cart item not exist
     * @throws BadRequestException if shopping cart already DONE
     */
    @PutMapping("/{id}")
    public CartItemResponse update(@PathVariable("id") long cartItemId,
                                   @Valid @RequestBody CartItemUpdate cartItemUpdate,
                                   HttpServletRequest request) {
        // Get user id from request
        Long userId = TokenAuthenticationService.getUserId(getToken(request));

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), userId);

        // Throw Resource not found exception when no shopping cart by given shopping cart id and user id
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Throw bad request exception when shopping cart is already done
        if (isShoppingCartDone(shoppingCart)) {
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

        return cartItemMapper.toCartItemResponse(cartItem);
    }

    /**
     * Delete cart item in given shopping cart
     *
     * @param cartItemId     Cart item id
     * @param shoppingCartId Shopping cart id
     * @param request        Request from user
     * @return Deleted cart item response
     * @throws ResourceNotFoundException if shopping cart or cart item not exist
     */
    @DeleteMapping("/{id}")
    public CartItemResponse delete(@PathVariable("id") long cartItemId,
                                   @RequestParam long shoppingCartId,
                                   HttpServletRequest request) {
        log.debug("Delete /cart-items/{}?shoppingCartId={}", cartItemId, shoppingCartId);

        // Get user id from request
        Long userId = TokenAuthenticationService.getUserId(getToken(request));

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
