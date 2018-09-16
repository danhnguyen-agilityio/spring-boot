package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.configs.SecurityConfig;
import com.agility.shopping.cart.constants.MessageConstant;
import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.dto.ShoppingCartRequest;
import com.agility.shopping.cart.dto.ShoppingCartResponse;
import com.agility.shopping.cart.exceptions.BadRequestException;
import com.agility.shopping.cart.exceptions.ResourceAlreadyExistsException;
import com.agility.shopping.cart.exceptions.ResourceForbiddenException;
import com.agility.shopping.cart.exceptions.ResourceNotFoundException;
import com.agility.shopping.cart.mappers.ShoppingCartMapper;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.repositories.ShoppingCartRepository;
import com.agility.shopping.cart.repositories.UserRepository;
import com.agility.shopping.cart.services.TokenAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

import static com.agility.shopping.cart.exceptions.CustomError.*;

/**
 * This class implement api that relate to Shopping cart data
 */
@RestController
@RequestMapping("/shopping-carts")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    /**
     * Create shopping cart
     *
     * @param shoppingCartRequest Shopping cart request
     * @param request Request from user
     * @return Shopping cart response
     */
    @PostMapping
    public ShoppingCartResponse create(@Valid @RequestBody ShoppingCartRequest shoppingCartRequest,
                                       HttpServletRequest request) {

        log.debug("POST /shopping-carts, body = {}", shoppingCartRequest);

        boolean existedName = shoppingCartRepository.
            existsByName(shoppingCartRequest.getName());

        // Throw resource exist exception product name already exists
        if (existedName) {
            throw new ResourceAlreadyExistsException(SHOPPING_CART_EXIST);
        }

        // Get user id;
        long userId = tokenAuthenticationService.getUserId(getToken(request));

        // Get user by user id
        User user = userRepository.findOne(userId);

        // Throw not found exception when user null
        if (user == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        // Convert to shopping cart
        ShoppingCart shoppingCart = shoppingCartMapper.toShoppingCart(shoppingCartRequest);

        // Set user info for shopping cart
        shoppingCart.setUser(user);

        // Save database
        shoppingCart = shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toShoppingCartResponse(shoppingCart);
    }

    /**
     * Find all shopping cart of current request user request
     *
     * @return List shopping cart response of request user
     */
    @GetMapping
    public List<ShoppingCartResponse> findAll() {

        // Get username from request
        String username = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

        // Get list shopping cart
        List<ShoppingCart> shoppingCarts =
            shoppingCartRepository.findAllByUsername(username);

        return shoppingCartMapper.toShoppingCartResponse(shoppingCarts);
    }

    /**
     * Find one shopping cart by given id
     *
     * @param id      Shopping cart id
     * @param request Request from user
     * @return Cart item response with given id
     * @throws ResourceNotFoundException if shopping cart not exist
     */
    @GetMapping("/{id}")
    public ShoppingCartResponse findOne(@PathVariable long id, HttpServletRequest request) {
        log.debug("GET /shopping-carts/{}", id);
        // Get user id from request
        Long userId = tokenAuthenticationService.getUserId(getToken(request));

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(id, userId);

        // Throw Resource not found exception when shopping cart not exist
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        return shoppingCartMapper.toShoppingCartResponse(shoppingCart);
    }

    /**
     * Update shopping cart with given id
     *
     * @param id                  Shopping cart id
     * @param shoppingCartRequest Shopping cart request
     * @return Shopping cart response
     * @throws ResourceNotFoundException      if shopping cart id does not exist
     * @throws ResourceForbiddenException     if authenticated user not own shopping cart with given id
     * @throws ResourceAlreadyExistsException if shopping cart name belong to other shopping cart (not contain given id)
     */
    @PutMapping("/{id}")
    public ShoppingCartResponse update(@PathVariable long id,
                                       @Valid @RequestBody ShoppingCartRequest shoppingCartRequest,
                                       HttpServletRequest request) {

        // Get user id from request
        Long userId = tokenAuthenticationService.getUserId(getToken(request));

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(id, userId);

        // Throw Resource not found exception when shopping cart not exist
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Throw resource exists exception when shopping cart name belong to other shopping cart (not contain given id)
        if (!shoppingCart.getName().equals(shoppingCartRequest.getName())
            && shoppingCartRepository.existsByName(shoppingCartRequest.getName())) {
            throw new ResourceAlreadyExistsException(SHOPPING_CART_EXIST);
        }

        // Update shopping cart when name of shopping cart with given id not change
        // or shopping cart name does not exist
        shoppingCart.setName(shoppingCartRequest.getName());
        shoppingCart.setDescription(shoppingCartRequest.getDescription());
        shoppingCart = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toShoppingCartResponse(shoppingCart);
    }

    /**
     * Delete shopping cart by given id
     *
     * @param id Shopping cart id
     * @return Message success
     * @throws ResourceNotFoundException  if shopping cart id does not exist
     * @throws ResourceForbiddenException if authenticated user not own shopping cart with given id
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, HttpServletRequest request) {
        // Get user id from request
        Long userId = tokenAuthenticationService.getUserId(getToken(request));

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(id, userId);

        // Throw Resource not found exception when shopping cart not exist
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Delete shopping cart when authenticated user own shopping cart with given id
        shoppingCartRepository.delete(shoppingCart);

        return MessageConstant.SHOPPING_CART_DELETE_SUCCESS;
    }


    /**
     * Checkout shopping cart by given id
     *
     * @param id      Shopping cart id
     * @param request Request from user
     * @return Message success
     * @throws ResourceNotFoundException if shopping cart not exist
     * @throws BadRequestException       if shopping cart done or shopping cart empty
     */
    @PostMapping("/{id}/checkout")
    public String checkout(@PathVariable long id, HttpServletRequest request) {

        // Get user id from request
        Long userId = tokenAuthenticationService.getUserId(getToken(request));

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(id, userId);

        // Throw Resource not found exception when shopping cart not exist
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Throw Bad request exception when shopping cart done
        if (isShoppingCartWithStatus(shoppingCart, ShoppingCartStatus.DONE)) {
            throw new BadRequestException(SHOPPING_CART_DONE);
        }

        // Throw Bad request exception when shopping cart empty
        if (isShoppingCartWithStatus(shoppingCart, ShoppingCartStatus.EMPTY)) {
            throw new BadRequestException(SHOPPING_CART_EMPTY);
        }

        // Change status from IN_PROGRESS to COMPLETE
        shoppingCart.setStatus(ShoppingCartStatus.DONE.getName());
        shoppingCartRepository.save(shoppingCart);

        return MessageConstant.SHOPPING_CART_CHECKOUT_SUCCESS;
    }

    /**
     * Get token from request
     *
     * @param request Request from user
     * @return Token string
     */
    private String getToken(HttpServletRequest request) {
        return request.getHeader(securityConfig.getHeaderString());
    }

    /**
     * Check whether or not shopping cart have given shopping cart status
     *
     * @param shoppingCart Shopping cart need check
     * @param status       Status shopping cart
     * @return true if shopping cart have given status, else return false
     */
    private boolean isShoppingCartWithStatus(ShoppingCart shoppingCart, ShoppingCartStatus status) {
        return status.getName().equals(shoppingCart.getStatus());
    }

}
