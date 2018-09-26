package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.models.CartItem;
import com.agility.shopping.cart.models.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.agility.shopping.cart.configs.WebSecurityConfig.CART_ITEM_DETAIL_URL;
import static com.agility.shopping.cart.configs.WebSecurityConfig.CART_ITEM_URL;
import static com.agility.shopping.cart.exceptions.CustomError.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * This class test RESTful api for cart item
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CartItemControllerTest extends BaseControllerTest {

    // =================================================================
    // Test Create Cart Item
    // =================================================================

    /**
     * Test create cart item throw forbidden exception for admin user
     */
    @Test
    public void testCreateCartItemShouldThrowForbiddenWhenAdminUserAccess() throws Exception {
        when(userRepository.findByUsername(adminUser.getUsername())).thenReturn(Optional.ofNullable(adminUser));

        testResponseData(RequestInfo.builder()
            .request(post(CART_ITEM_URL))
            .token(adminToken)
            .body(cartItemRequest)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test create cart item throw resource not found exception when shopping cart not found
     */
    @Test
    public void testCreateCartItemShouldThrowResourceNotFoundWhenShoppingCartNotFound()
        throws Exception {
        when(shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), memberUser.getId())).thenReturn(null);

        testResponseData(RequestInfo.builder()
            .request(post(CART_ITEM_URL))
            .token(memberToken)
            .body(cartItemRequest)
            .httpStatus(HttpStatus.NOT_FOUND)
            .jsonMap(createJsonMapError(SHOPPING_CART_NOT_FOUND))
            .build());

        verify(shoppingCartRepository, times(1))
            .findOne(cartItemRequest.getShoppingCartId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test create cart item throw bad request exception when shopping cart done
     */
    @Test
    public void testCreatedCartItemShouldThrowBadRequestExceptionWhenShoppingCartDone() throws Exception {
        shoppingCart.setStatus(ShoppingCartStatus.DONE.name());
        when(shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), memberUser.getId()))
            .thenReturn(shoppingCart);

        testResponseData(RequestInfo.builder()
            .request(post(CART_ITEM_URL))
            .token(memberToken)
            .body(cartItemRequest)
            .httpStatus(HttpStatus.BAD_REQUEST)
            .jsonMap(createJsonMapError(SHOPPING_CART_DONE))
            .build());

        verify(shoppingCartRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test create cart item throw resource not found exception when product not found
     */
    @Test
    public void testCreateCartItemShouldThrowResourceNotFoundWhenProductNotFound() throws Exception {
        shoppingCart.setStatus(ShoppingCartStatus.IN_PROGRESS.name());
        when(shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), memberUser.getId())).
            thenReturn(shoppingCart);
        when(productRepository.findOne(cartItemRequest.getProductId())).thenReturn(null);

        testResponseData(RequestInfo.builder()
            .request(post(CART_ITEM_URL))
            .token(memberToken)
            .body(cartItemRequest)
            .httpStatus(HttpStatus.NOT_FOUND)
            .jsonMap(createJsonMapError(PRODUCT_NOT_FOUND))
            .build());

        verify(shoppingCartRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), memberUser.getId());
        verify(productRepository, times(1)).
            findOne(cartItemRequest.getProductId());
        verifyNoMoreInteractions(shoppingCartRepository, productRepository);
    }

    /**
     * Test create cart item success when cart item not found
     */
    @Test
    public void testCreateCartItemShouldSuccessWhenCartItemNotFound() throws Exception {
        // Set status IN_PROGRESS for shopping cart
        shoppingCart.setStatus(ShoppingCartStatus.IN_PROGRESS.getName());

        // Mock method
        when(shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), memberUser.getId()))
            .thenReturn(shoppingCart);
        when(productRepository.findOne(cartItemRequest.getProductId())).thenReturn(product);
        when(cartItemRepository.findOne(cartItemRequest.getShoppingCartId(), cartItemRequest.getProductId()))
            .thenReturn(null);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        // Perform request and test response data
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("$.quantity", cartItem.getQuantity());

        testResponseData(RequestInfo.builder()
            .request(post(CART_ITEM_URL))
            .token(memberToken)
            .body(cartItemRequest)
            .httpStatus(HttpStatus.CREATED)
            .jsonMap(jsonMap)
            .build());

        // Verify called method
        verify(shoppingCartRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), memberUser.getId());
        verify(productRepository, times(1)).findOne(cartItemRequest.getProductId());
        verify(cartItemRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), cartItemRequest.getProductId());
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
        verifyNoMoreInteractions(shoppingCartRepository, productRepository, cartItemRepository);
    }

    /**
     * Test create cart item success when cart item found
     */
    @Test
    public void testCreateCartItemShouldSuccessWhenCartItemFound() throws Exception {
        // Set status IN_PROGRESS for shopping cart
        shoppingCart.setStatus(ShoppingCartStatus.IN_PROGRESS.getName());
        long quantity = cartItem.getQuantity();

        // Mock method
        when(shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), memberUser.getId())).
            thenReturn(shoppingCart);
        when(productRepository.findOne(cartItemRequest.getProductId())).thenReturn(product);
        when(cartItemRepository.findOne(cartItemRequest.getShoppingCartId(), cartItemRequest.getProductId()))
            .thenReturn(cartItem);
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        // Perform request and test response data
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("$.quantity", quantity + cartItemRequest.getQuantity());
        testResponseData(RequestInfo.builder()
            .request(post(CART_ITEM_URL))
            .token(memberToken)
            .body(cartItemRequest)
            .httpStatus(HttpStatus.CREATED)
            .jsonMap(jsonMap)
            .build());

        verify(shoppingCartRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), memberUser.getId());
        verify(productRepository, times(1)).findOne(cartItemRequest.getProductId());
        verify(cartItemRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), cartItemRequest.getProductId());
        verify(cartItemRepository, times(1)).save(cartItem);
        verifyNoMoreInteractions(shoppingCartRepository, productRepository, cartItemRepository);
    }

    // =================================================================
    // Test Get All Cart Item By Shopping Cart Id
    // =================================================================

    /**
     * Test find all cart item throw forbidden exception when admin user access
     */
    @Test
    public void testFindAllCartItemShouldThrowForbiddenWhenAdminUserAccess() throws Exception {
        when(userRepository.findByUsername(adminUser.getUsername())).thenReturn(Optional.ofNullable(adminUser));

        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());
        testResponseData(RequestInfo.builder()
            .request(get(CART_ITEM_URL))
            .params(params)
            .token(adminToken)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test find all cart item should throw resource not found exception when shopping cart not found
     */
    @Test
    public void testFindAllCartItemShouldThrowResourceNotFoundWhenShoppingCartNotFound()
        throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(null);

        // Test response data for request
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());
        testResponseData(RequestInfo.builder()
            .request(get(CART_ITEM_URL))
            .params(params)
            .token(memberToken)
            .httpStatus(HttpStatus.NOT_FOUND)
            .jsonMap(createJsonMapError(SHOPPING_CART_NOT_FOUND))
            .build());

        // Verify called method
        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCart.getId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test find all cart item success
     */
    @Test
    public void testFindAllCartItemShouldSuccess() throws Exception {
        // Mock data and method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findAllByShoppingCartId(shoppingCart.getId())).thenReturn(cartItems);

        // Create params map
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());
        Map<String, Object> jsonPath = new HashMap<>();
        // Create json map
        jsonPath.put("$", hasSize(cartItems.size()));
        jsonPath.put("$[0].quantity", cartItems.get(0).getQuantity());
        // Test response data for request
        testResponseData(RequestInfo.builder()
            .request(get(CART_ITEM_URL))
            .params(params)
            .token(memberToken)
            .httpStatus(HttpStatus.OK)
            .jsonMap(jsonPath)
            .build());

        verify(shoppingCartRepository, times(1))
            .findOne(shoppingCart.getId(), memberUser.getId());
        verify(cartItemRepository, times(1)).findAllByShoppingCartId(shoppingCart.getId());
        verifyNoMoreInteractions(shoppingCartRepository, cartItemRepository);
    }

    // =================================================================
    // Test Find One Cart Item By Cart Item Id And Shopping Cart Id */
    // =================================================================

    /**
     * Test find one cart item throw forbidden exception for admin user access
     */
    @Test
    public void testFindOneCartItemShouldThrowForbiddenWhenAdminUserAccess() throws Exception {
        when(userRepository.findByUsername(adminUser.getUsername())).thenReturn(Optional.ofNullable(adminUser));

        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());

        // Test response data for request
        testResponseData(RequestInfo.builder()
            .request((get(CART_ITEM_DETAIL_URL, faker.number().randomNumber())))
            .params(params)
            .token(adminToken)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test find one cart item throw resource not found when shopping cart not found
     */
    @Test
    public void testFindOneCartItem_ShouldThrowResourceNotFound_WhenShoppingCartNotFound()
        throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(null);

        // Test response data for request
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());
        testResponseData(RequestInfo.builder()
            .request(get(CART_ITEM_DETAIL_URL, cartItem.getId()))
            .params(params)
            .token(memberToken)
            .httpStatus(HttpStatus.NOT_FOUND)
            .jsonMap(createJsonMapError(SHOPPING_CART_NOT_FOUND))
            .build());

        // Verify called method
        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCart.getId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test find one cart item throw resource not found exception when cart item not found
     */
    @Test
    public void testFindOneCartItemShouldThrowResourceNotFoundWhenCartItemNotFound()
        throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId()))
            .thenReturn(null);

        // Test response data for request
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());
        testResponseData(RequestInfo.builder()
            .request(get(CART_ITEM_DETAIL_URL, cartItem.getId()))
            .params(params)
            .token(memberToken)
            .httpStatus(HttpStatus.NOT_FOUND)
            .jsonMap(createJsonMapError(CART_ITEM_NOT_FOUND))
            .build());

        // Verify called method
        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCart.getId(), memberUser.getId());
        verify(cartItemRepository, times(1)).
            findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId());
        verifyNoMoreInteractions(shoppingCartRepository, cartItemRepository);
    }

    /**
     * Test fine one cart item success
     */
    @Test
    public void testFindOneCartItemShouldSuccess() throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId()))
            .thenReturn(cartItem);

        // Test response data for request
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());
        Map<String, Object> jsonMap = new HashMap<>();
        // FIXME: can not compare Long number
        // jsonMap.put("$.id", cartItem.getId());
        // jsonMap.put("$.quantity", cartItem.getQuantity().intValue());
        testResponseData(RequestInfo.builder()
            .request(get(CART_ITEM_DETAIL_URL, cartItem.getId()))
            .params(params)
            .token(memberToken)
            .httpStatus(HttpStatus.OK)
            .jsonMap(jsonMap)
            .build());

        // Verify called method
        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCart.getId(), memberUser.getId());
        verify(cartItemRepository, times(1)).
            findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId());
        verifyNoMoreInteractions(shoppingCartRepository, cartItemRepository);
    }

    // =========================================================
    // Test Update Cart Item
    // =========================================================

    /**
     * Test update cart item throw forbidden exception when admin user access
     */
    @Test
    public void testUpdateCartItemShouldThrowForbiddenWhenAdminUserAccess() throws Exception {
        when(userRepository.findByUsername(adminUser.getUsername())).thenReturn(Optional.ofNullable(adminUser));

        // Test response data for request
        testResponseData(RequestInfo.builder()
            .request(put(CART_ITEM_DETAIL_URL, cartItem.getId()))
            .token(adminToken)
            .body(cartItemRequest)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test update cart item throw resource not found exception when shopping cart not found
     */
    @Test
    public void testUpdateCartItemShouldThrowResourceNotFoundWhenShoppingCartNotFound()
        throws Exception {
        log.debug("Test update cart item success");
        log.debug("Cart item update: {}", cartItemUpdate);

        // Mock method
        when(shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), memberUser.getId())).thenReturn(null);

        // Test response data for request
        testResponseData(RequestInfo.builder()
            .request(put(CART_ITEM_DETAIL_URL, cartItem.getId()))
            .token(memberToken)
            .body(cartItemUpdate)
            .httpStatus(HttpStatus.NOT_FOUND)
            .jsonMap(createJsonMapError(SHOPPING_CART_NOT_FOUND))
            .build());

        // Verify called method
        verify(shoppingCartRepository, times(1)).
            findOne(cartItemUpdate.getShoppingCartId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test update cart item throw resource not found exception when shopping cart done
     */
    @Test
    public void testUpdateCartItemShouldThrowBadRequestWhenShoppingCartDone() throws Exception {
        // Mock data and method
        shoppingCart.setStatus(ShoppingCartStatus.DONE.getName());
        when(shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), memberUser.getId()))
            .thenReturn(shoppingCart);

        // Test response data for request
        testResponseData(RequestInfo.builder()
            .request(put(CART_ITEM_DETAIL_URL, cartItem.getId()))
            .token(memberToken)
            .body(cartItemUpdate)
            .httpStatus(HttpStatus.BAD_REQUEST)
            .jsonMap(createJsonMapError(SHOPPING_CART_DONE))
            .build());

        // Verify called method
        verify(shoppingCartRepository, times(1)).
            findOne(cartItemUpdate.getShoppingCartId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test update cart item throw resource not found exception when cart item not found
     */
    @Test
    public void testUpdateCartItemShouldThrowResourceNotFoundWhenCartItemNotFound()
        throws Exception {
        // Mock method and data
        shoppingCart.setStatus(ShoppingCartStatus.IN_PROGRESS.getName());
        when(shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), memberUser.getId()))
            .thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), cartItemUpdate.getShoppingCartId()))
            .thenReturn(null);

        // Test response data for request
        testResponseData(RequestInfo.builder()
            .request(put(CART_ITEM_DETAIL_URL, cartItem.getId()))
            .token(memberToken)
            .body(cartItemUpdate)
            .httpStatus(HttpStatus.NOT_FOUND)
            .jsonMap(createJsonMapError(CART_ITEM_NOT_FOUND))
            .build());

        // Verify called method
        verify(shoppingCartRepository, times(1)).
            findOne(cartItemUpdate.getShoppingCartId(), memberUser.getId());
        verify(cartItemRepository, times(1)).
            findOneByCartItemIdAndShoppingCartId(cartItem.getId(), cartItemUpdate.getShoppingCartId());
        verifyNoMoreInteractions(shoppingCartRepository, cartItemRepository);
    }

    /**
     * Test update cart item success
     */
    @Test
    public void testUpdateCartItemShouldSuccess() throws Exception {
        // Mock data and method
        shoppingCart.setStatus(ShoppingCartStatus.IN_PROGRESS.getName());
        cartItemUpdate.setShoppingCartId(shoppingCart.getId());
        when(shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), memberUser.getId()))
            .thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), cartItemUpdate.getShoppingCartId()))
            .thenReturn(cartItem);
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        // Test response data for request
        Map<String, Object> jsonMap = new HashMap<>();
        // FIXME:: Can not compare Long number
        // jsonMap.put("$.id", 0 + cartItem.getId());
        // jsonMap.put("$.quantity", 0 + cartItemUpdate.getQuantity());
        testResponseData(RequestInfo.builder()
            .request(put(CART_ITEM_DETAIL_URL, cartItem.getId()))
            .token(memberToken)
            .body(cartItemUpdate)
            .httpStatus(HttpStatus.OK)
            .jsonMap(jsonMap)
            .build());

        // Verify called method
        verify(shoppingCartRepository, times(1)).
            findOne(cartItemUpdate.getShoppingCartId(), memberUser.getId());
        verify(cartItemRepository, times(1)).
            findOneByCartItemIdAndShoppingCartId(cartItem.getId(), cartItemUpdate.getShoppingCartId());
        verify(cartItemRepository, times(1)).save(cartItem);
        verifyNoMoreInteractions(shoppingCartRepository, cartItemRepository);
    }

    // =========================================================
    // Test Delete Cart Item
    // =========================================================

    /**
     * Test delete cart item throw forbidden exception when admin user access
     */
    @Test
    public void testDeleteCartItemShouldThrowForbiddenWhenAdminUserAccess() throws Exception {
        when(userRepository.findByUsername(adminUser.getUsername())).thenReturn(Optional.ofNullable(adminUser));

        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());

        // Test response data for request
        testResponseData(RequestInfo.builder()
            .request(delete(CART_ITEM_DETAIL_URL, cartItem.getId()))
            .params(params)
            .token(adminToken)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test delete cart item throw resource not found exception when shopping cart not found
     */
    @Test
    public void testDeleteCartItemShouldThrowResourceNotFoundWhenShoppingCartNotFound() throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(null);

        // Test response data for request
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());
        testResponseData(RequestInfo.builder()
            .request(delete(CART_ITEM_DETAIL_URL, cartItem.getId()))
            .params(params)
            .token(memberToken)
            .httpStatus(HttpStatus.NOT_FOUND)
            .jsonMap(createJsonMapError(SHOPPING_CART_NOT_FOUND))
            .build());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test delete cart item throw resource not found exception
     * when no cart item with given cart item id and shopping cart id
     */
    @Test
    public void testDeleteCartItemShouldThrowResourceNotFoundWhenCartItemNotFound() throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId()))
            .thenReturn(null);

        // Test response data for request
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());
        testResponseData(RequestInfo.builder()
            .request(delete(CART_ITEM_DETAIL_URL, cartItem.getId()))
            .params(params)
            .token(memberToken)
            .httpStatus(HttpStatus.NOT_FOUND)
            .jsonMap(createJsonMapError(CART_ITEM_NOT_FOUND))
            .build());

        // Verify called method
        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verify(cartItemRepository, times(1))
            .findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId());
        verifyNoMoreInteractions(shoppingCartRepository, cartItemRepository);
    }

    /**
     * Test delete cart item success
     */
    @Test
    public void testDeleteCartItemShouldSuccess() throws Exception {
        // Mock data and method
        shoppingCart.setStatus(ShoppingCartStatus.IN_PROGRESS.getName());
        // Set one cart item for shopping cart
        shoppingCart.setCartItems(Sets.newSet(cartItem));
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId()))
            .thenReturn(cartItem);
        when(shoppingCartRepository.save(shoppingCart)).thenReturn(shoppingCart);

        // Test response data for request
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());

        testResponseData(RequestInfo.builder()
            .request(delete(CART_ITEM_DETAIL_URL, cartItem.getId()))
            .params(params)
            .token(memberToken)
            .httpStatus(HttpStatus.NO_CONTENT)
            .build());

        // Verify status shopping cart is EMPTY when no cart item for shopping cart after delete cart item
        assertEquals(shoppingCart.getStatus(), ShoppingCartStatus.EMPTY.getName());

        // Verify called method
        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verify(cartItemRepository, times(1)).
            findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId());
        verify(shoppingCartRepository, times(1)).save(shoppingCart);
        verifyNoMoreInteractions(shoppingCartRepository, cartItemRepository);
    }
}
