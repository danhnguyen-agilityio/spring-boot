package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.dto.ShoppingCartRequest;
import com.agility.shopping.cart.dto.ShoppingCartResponse;
import com.agility.shopping.cart.exceptions.ResourceAlreadyExistsException;
import com.agility.shopping.cart.exceptions.ResourceForbiddenException;
import com.agility.shopping.cart.exceptions.ResourceNotFoundException;
import com.agility.shopping.cart.mappers.ShoppingCartMapper;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.repositories.ShoppingCartRepository;
import com.agility.shopping.cart.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Create shopping cart
     *
     * @param request Shopping cart request
     * @return Shopping cart response
     */
    @PostMapping
    public ShoppingCartResponse create(
        @Valid @RequestBody ShoppingCartRequest request) {

        log.debug("POST /shopping-carts, body = {}", request);

        boolean existedName = shoppingCartRepository.
            existsByName(request.getName());

        // Throw resource exist exception product name already exists
        if (existedName) {
            throw new ResourceAlreadyExistsException(SHOPPING_CART_EXIST);
        }

        // Get username from request
        String username = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();
        log.debug("Username request: {}", username);

        // Get user by username
        User user = userRepository.findByUsername(username);
        log.debug("User: {}", user);

        // FIXME:: Consider resolve when get authentication or in here
        // Throw not found exception when user null
        if (user == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        // Convert to shopping cart
        ShoppingCart shoppingCart = shoppingCartMapper.toShoppingCart(request);

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
     * Update shopping cart with given id
     *
     * @param id      Shopping cart id
     * @param request Shopping cart request
     * @return Shopping cart response
     * @throws ResourceNotFoundException      if shopping cart id does not exist
     * @throws ResourceForbiddenException     if authenticated user not own shopping cart with given id
     * @throws ResourceAlreadyExistsException if shopping cart name belong to other shopping cart (not contain given id)
     */
    @PutMapping("/{id}")
    public ShoppingCartResponse update(@PathVariable long id, @Valid @RequestBody ShoppingCartRequest request) {

        ShoppingCart shoppingCart = shoppingCartRepository.findOne(id);

        // Throw not found exception when shopping cart id does not exist
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Get authenticated username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Throw forbidden exception when authenticated user not own shopping cart with given id
        if (!username.equals(shoppingCart.getUser().getUsername())) {
            throw new ResourceForbiddenException(SHOPPING_CART_FORBIDDEN);
        }

        // Throw resource exists exception when shopping cart name belong to other shopping cart (not contain given id)
        if (!shoppingCart.getName().equals(request.getName())
            && shoppingCartRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException(SHOPPING_CART_EXIST);
        }

        // Update shopping cart
        shoppingCart = shoppingCartMapper.toShoppingCart(request);
        shoppingCart = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toShoppingCartResponse(shoppingCart);
    }


}
