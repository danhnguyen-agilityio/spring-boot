package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.constants.MessageConstant;
import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.dto.ShoppingCartRequest;
import com.agility.shopping.cart.dto.ShoppingCartResponse;
import com.agility.shopping.cart.exceptions.BadRequestException;
import com.agility.shopping.cart.exceptions.ResourceAlreadyExistsException;
import com.agility.shopping.cart.exceptions.ResourceForbiddenException;
import com.agility.shopping.cart.exceptions.ResourceNotFoundException;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.securities.RoleConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
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
@Secured(RoleConstant.MEMBER)
public class ShoppingCartController extends BaseController {

    /**
     * Create shopping cart
     *
     * @param shoppingCartRequest Shopping cart request
     * @return Shopping cart response
     */
    // TODO: Test this API
    @PostMapping
    public ShoppingCartResponse create(@Valid @RequestBody ShoppingCartRequest shoppingCartRequest) {

        log.debug("POST /shopping-carts, body = {}", shoppingCartRequest);

        boolean existedName = shoppingCartRepository.existsByName(shoppingCartRequest.getName());

        // Throw resource exist exception shopping cart name already exists
        if (existedName) {
            throw new ResourceAlreadyExistsException(SHOPPING_CART_EXIST);
        }

        // Convert to shopping cart
        ShoppingCart shoppingCart = shoppingCartMapper.toShoppingCart(shoppingCartRequest);

        // Set user info for shopping cart
        shoppingCart.setUser(securityService.getCurrentUser());

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

        User user = securityService.getCurrentUser();

        // Get list shopping cart
        List<ShoppingCart> shoppingCarts =
            shoppingCartRepository.findAllByUsername(user.getUsername());

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
        Long userId = jwtTokenService.getUserId(request);

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
        Long userId = jwtTokenService.getUserId(request);

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
        Long userId = jwtTokenService.getUserId(request);

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
        Long userId = jwtTokenService.getUserId(request);

        // Get shopping cart by shopping cart id and user id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(id, userId);

        // Throw Resource not found exception when shopping cart not exist
        if (shoppingCart == null) {
            throw new ResourceNotFoundException(SHOPPING_CART_NOT_FOUND);
        }

        // Throw Bad request exception when shopping cart done
        if (shoppingCartService.haveStatus(shoppingCart, ShoppingCartStatus.DONE)) {
            throw new BadRequestException(SHOPPING_CART_DONE);
        }

        // Throw Bad request exception when shopping cart empty
        if (shoppingCartService.haveStatus(shoppingCart, ShoppingCartStatus.EMPTY)) {
            throw new BadRequestException(SHOPPING_CART_EMPTY);
        }

        // Change status from IN_PROGRESS to COMPLETE
        shoppingCart.setStatus(ShoppingCartStatus.DONE.getName());
        shoppingCartRepository.save(shoppingCart);

        return MessageConstant.SHOPPING_CART_CHECKOUT_SUCCESS;
    }

}
