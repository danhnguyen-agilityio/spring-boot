package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.models.RequestInfo;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static com.agility.shopping.cart.configs.WebSecurityConfig.SHOPPING_CART_DETAIL_URL;
import static com.agility.shopping.cart.exceptions.CustomError.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * This class test RESTful api for shopping cart
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShoppingCartControllerTest extends BaseControllerTest {

    private static final String SHOPPING_CART_ENDPOINT = "/shopping-carts";
    private static final String SHOPPING_CART_DETAIL_ENDPOINT = "/shopping-carts/{id}";
    private static final String SHOPPING_CART_CHECKOUT_ENDPOINT = "/shopping-carts/{id}/checkout";

    // ============================================================
    // Test create shopping cart
    // ============================================================

    /**
     * Test create shopping cart throw forbidden exception when admin user access
     */
    @Test
    public void testCreateShoppingCartShouldThrowForbiddenWhenAdminUserAccess() throws Exception {
        // Test response data for request
        testResponseData(RequestInfo.builder()
            .request(post(SHOPPING_CART_ENDPOINT))
            .token(adminToken)
            .body(shoppingCartRequest)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test create shopping cart fail resource exists when shopping cart name is already existed
     */
    @Test
    public void testCreateShoppingCartShouldThrowResourceExistsWhenNameExisted() throws Exception {
        // Mock method
        when(shoppingCartRepository.existsByName(shoppingCartRequest.getName())).thenReturn(true);

        testResponseData(RequestInfo.builder()
            .request(post(SHOPPING_CART_ENDPOINT))
            .token(memberToken)
            .body(shoppingCartRequest)
            .httpStatus(HttpStatus.CONFLICT)
            .build());

        verify(shoppingCartRepository, times(1)).existsByName(shoppingCartRequest.getName());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test create shopping cart should correct
     */
    @Test
    public void testCreateShoppingCartSuccess() throws Exception {
        // Mock method
        when(shoppingCartRepository.existsByName(shoppingCartRequest.getName())).thenReturn(false);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(shoppingCart);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("$.user.username", memberUser.getUsername());
        testResponseData(RequestInfo.builder()
            .request(post(SHOPPING_CART_ENDPOINT))
            .token(memberToken)
            .body(shoppingCartRequest)
            .httpStatus(HttpStatus.OK)
            .jsonMap(jsonMap)
            .build());

        verify(shoppingCartRepository, times(1)).existsByName(shoppingCartRequest.getName());
        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    // ============================================================
    // Test find all shopping cart
    // ============================================================

    /**
     * Test fina all shopping cart throw forbidden exception when admin user access
     */
    @Test
    public void testFindAllShoppingCartShouldThrowForbiddenWhenAdminUserAccess() throws Exception {
        // Test response data for request
        testResponseData(RequestInfo.builder()
            .request(get(SHOPPING_CART_ENDPOINT))
            .token(adminToken)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test find all shopping cart should correct
     */
    @Test
    public void testFindAllShoppingCartShouldCorrect() throws Exception {
        // Mock method
        when(shoppingCartRepository.findAllByUsername(memberUser.getUsername())).thenReturn(shoppingCarts);

        testResponseData(RequestInfo.builder()
            .request(get(SHOPPING_CART_ENDPOINT))
            .token(memberToken)
            .httpStatus(HttpStatus.OK)
            .build());

        verify(shoppingCartRepository, times(1)).findAllByUsername(memberUser.getUsername());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    // ============================================================
    // Test find one shopping cart
    // ============================================================

    /**
     * Test find one shopping cart throw forbidden exception for admin user
     */
    @Test
    public void testFindOneShoppingCartShouldThrowForbiddenExceptionForAdminUserAccess() throws Exception {
        testResponseData(RequestInfo.builder()
            .request(get(SHOPPING_CART_DETAIL_ENDPOINT, shoppingCart.getId()))
            .token(adminToken)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test find one shopping cart should throw resource not found exception when shopping cart not found
     */
    @Test
    public void testFineOneShoppingCartShouldThrowResourceNotFoundWhenShoppingCartNotFound() throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(null);

        testResponseData(RequestInfo.builder()
            .request(get(SHOPPING_CART_DETAIL_URL, shoppingCart.getId()))
            .token(memberToken)
            .httpStatus(HttpStatus.NOT_FOUND)
            .build());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test find one shopping cart should correct
     */
    @Test
    public void testFineOneShoppingCartShouldCorrect() throws Exception {
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);

        testResponseData(RequestInfo.builder()
            .request(get(SHOPPING_CART_DETAIL_URL, shoppingCart.getId()))
            .token(memberToken)
            .httpStatus(HttpStatus.OK)
            .build());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }


    // ============================================================
    // Test update shopping cart
    // ============================================================

    /**
     * Test update shopping cart should throw forbidden when admin user access
     */
    @Test
    public void testUpdateShoppingCartShouldThrowForbiddenWhenAdminUserAccess() throws Exception {
        testResponseData(RequestInfo.builder()
            .request(put(SHOPPING_CART_DETAIL_URL, shoppingCart.getId()))
            .token(adminToken)
            .body(shoppingCartRequest)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test update shopping cart should throw resource not found when shopping cart not found
     */
    @Test
    public void testUpdateShoppingCartShouldThrowResourceNotFoundWhenShoppingCartNotFound() throws Exception {
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(null);

        testResponseData(RequestInfo.builder()
            .request(put(SHOPPING_CART_DETAIL_URL, shoppingCart.getId()))
            .token(memberToken)
            .body(shoppingCartRequest)
            .httpStatus(HttpStatus.NOT_FOUND)
            .build());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test update shopping cart should throw resource exist exception when new name is already existed
     */
    @Test
    public void testUpdateShoppingCartShouldThrowResourceExistWhenNewNameAlreadyExisted() throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);
        when(shoppingCartRepository.existsByName(shoppingCartRequest.getName())).thenReturn(true);

        testResponseData(RequestInfo.builder()
            .request(put(SHOPPING_CART_DETAIL_URL, shoppingCart.getId()))
            .token(memberToken)
            .body(shoppingCartRequest)
            .httpStatus(HttpStatus.CONFLICT)
            .build());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verify(shoppingCartRepository, times(1)).existsByName(shoppingCartRequest.getName());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test update shopping cart should correct when shopping cart name not change
     */
    @Test
    public void testUpdateShoppingCartShouldCorrectWhenShoppingCartNameNotChange() throws Exception {
        shoppingCartRequest.setName(shoppingCart.getName());

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);
        when(shoppingCartRepository.save(shoppingCart)).thenReturn(shoppingCart);

        testResponseData(RequestInfo.builder()
            .request(put(SHOPPING_CART_DETAIL_URL, shoppingCart.getId()))
            .token(memberToken)
            .body(shoppingCartRequest)
            .httpStatus(HttpStatus.OK)
            .build());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verify(shoppingCartRepository, times(1)).save(shoppingCart);
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test update shopping cart should correct when shopping cart name does not already existed
     */
    @Test
    public void testUpdateShoppingCartShouldCorrectWhenShoppingCartNameDoesNotAlreadyExisted() throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);
        when(shoppingCartRepository.existsByName(shoppingCartRequest.getName())).thenReturn(false);
        when(shoppingCartRepository.save(shoppingCart)).thenReturn(shoppingCart);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("$.name", shoppingCartRequest.getName());
        jsonMap.put("$.description", shoppingCartRequest.getDescription());
        testResponseData(RequestInfo.builder()
            .request(put(SHOPPING_CART_DETAIL_URL, shoppingCart.getId()))
            .token(memberToken)
            .body(shoppingCartRequest)
            .httpStatus(HttpStatus.OK)
            .jsonMap(jsonMap)
            .build());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verify(shoppingCartRepository, times(1)).existsByName(shoppingCartRequest.getName());
        verify(shoppingCartRepository, times(1)).save(shoppingCart);
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    // ============================================================
    // Test delete shopping cart
    // ============================================================

    /**
     * Test delete shopping cart fail forbidden exception for admin user
     */
    @Test
    public void testDeleteShoppingCartShouldThrowForbiddenWhenAdminUserAccess() throws Exception {
        testResponseData(RequestInfo.builder()
            .request(delete(SHOPPING_CART_DETAIL_URL, shoppingCart.getId()))
            .token(adminToken)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test delete shopping cart should throw resource not found when shopping cart not found
     */
    @Test
    public void testDeleteShoppingCartShouldThrowResourceNotFoundWhenShoppingCartNotFound() throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(null);

        testResponseData(RequestInfo.builder()
            .request(delete(SHOPPING_CART_DETAIL_URL, shoppingCart.getId()))
            .token(memberToken)
            .httpStatus(HttpStatus.NOT_FOUND)
            .build());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test delete shopping cart should correct
     */
    @Test
    public void testDeleteShoppingCartShouldCorrect() throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);
        doNothing().when(shoppingCartRepository).delete(shoppingCart);

        testResponseData(RequestInfo.builder()
            .request(delete(SHOPPING_CART_DETAIL_URL, shoppingCart.getId()))
            .token(memberToken)
            .httpStatus(HttpStatus.OK)
            .build());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verify(shoppingCartRepository, times(1)).delete(shoppingCart);
        verifyNoMoreInteractions(shoppingCartRepository);
    }


    // ============================================================
    // Test checkout shopping cart
    // ============================================================

    /**
     * Test checkout shopping cart throw forbidden exception when admin user access
     */
    @Test
    public void testCheckoutShoppingCartShouldThrowForbiddenExceptionWhenAdminUserAccess() throws Exception {
        testResponseData(RequestInfo.builder()
            .request(post(SHOPPING_CART_CHECKOUT_ENDPOINT , shoppingCart.getId()))
            .token(adminToken)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test checkout shopping cart throw resource not found when shopping cart not found
     */
    @Test
    public void testCheckoutShoppingCartShouldThrowResourceNotFoundWhenShoppingCartNotFound() throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(null);

        testResponseData(RequestInfo.builder()
            .request(post(SHOPPING_CART_CHECKOUT_ENDPOINT , shoppingCart.getId()))
            .token(memberToken)
            .httpStatus(HttpStatus.NOT_FOUND)
            .build());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test checkout shopping cart throw bad request exception when shopping cart done
     */
    @Test
    public void testCheckoutShoppingCartThrowBadRequestExceptionWhenShoppingCartDone() throws Exception {
        // Mock data
        shoppingCart.setStatus(ShoppingCartStatus.DONE.getName());

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);

        testResponseData(RequestInfo.builder()
            .request(post(SHOPPING_CART_CHECKOUT_ENDPOINT , shoppingCart.getId()))
            .token(memberToken)
            .httpStatus(HttpStatus.BAD_REQUEST)
            .jsonMap(createJsonMapError(SHOPPING_CART_DONE))
            .build());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test checkout shopping cart throw bad request exception when shopping cart empty
     */
    @Test
    public void testCheckoutShoppingCartShouldThrowBadRequestExceptionWhenShoppingCartEmpty() throws Exception {
        shoppingCart.setStatus(ShoppingCartStatus.EMPTY.getName());
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);

        testResponseData(RequestInfo.builder()
            .request(post(SHOPPING_CART_CHECKOUT_ENDPOINT , shoppingCart.getId()))
            .token(memberToken)
            .httpStatus(HttpStatus.BAD_REQUEST)
            .jsonMap(createJsonMapError(SHOPPING_CART_EMPTY))
            .build());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test checkout shopping cart should correct
     */
    @Test
    public void testCheckoutShoppingCartShouldCorrect() throws Exception {
        shoppingCart.setStatus(ShoppingCartStatus.IN_PROGRESS.getName());

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);
        when(shoppingCartRepository.save(shoppingCart)).thenReturn(shoppingCart);

        testResponseData(RequestInfo.builder()
            .request(post(SHOPPING_CART_CHECKOUT_ENDPOINT , shoppingCart.getId()))
            .token(memberToken)
            .httpStatus(HttpStatus.OK)
            .build());

        assertEquals(shoppingCart.getStatus(), ShoppingCartStatus.DONE.getName());
        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verify(shoppingCartRepository, times(1)).save(shoppingCart);
        verifyNoMoreInteractions(shoppingCartRepository);
    }

}
