package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.dto.ShoppingCartRequest;
import com.agility.shopping.cart.models.RequestInfo;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.agility.shopping.cart.configs.WebSecurityConfig.SHOPPING_CART_CHECKOUT_URL;
import static com.agility.shopping.cart.configs.WebSecurityConfig.SHOPPING_CART_DETAIL_URL;
import static com.agility.shopping.cart.exceptions.CustomError.*;
import static com.agility.shopping.cart.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class test RESTful api for shopping cart
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShoppingCartControllerTest extends BaseControllerTest {

    private static final String SHOPPING_CART_ENDPOINT = "/shopping-carts";
    private static final String SHOPPING_CART_DETAIL_ENDPOINT = "/shopping-carts/{id}";

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
            .request(get(SHOPPING_CART_DETAIL_URL, faker.number().randomNumber()))
            .token(adminToken)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test find one shopping cart should throw resource not found exception when shopping cart not found
     */
    @Test
    public void testFineOneShoppingCartShouldThrowResourceNotFoundWhenShoppingCartNotFound() throws Exception {
        // Mock member user
        User user = fakerService.fakeMemberUser();

        // Fake token
        String token = jwtTokenService.createToken(user);

        // Mock data
        Long shoppingCartId = faker.number().randomNumber();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCartId, user.getId())).thenReturn(null);

        // Call api
        mockMvc.perform(get(SHOPPING_CART_DETAIL_URL, shoppingCartId)
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(SHOPPING_CART_NOT_FOUND.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCartId, user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test find one shopping cart success
     */
    @Test
    public void testFineOneShoppingCartSuccess() throws Exception {
        // Mock member user
        User user = fakerService.fakeMemberUser();

        // Fake token
        String token = jwtTokenService.createToken(user);

        // Mock data
        Long shoppingCartId = faker.number().randomNumber();
        ShoppingCart shoppingCart = fakerService.fakeShoppingCart();
        shoppingCart.setId(shoppingCartId);
        shoppingCart.setCartItems(Sets.newSet(fakerService.fakeCartItem(), fakerService.fakeCartItem()));

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCartId, user.getId())).thenReturn(shoppingCart);

        // Call api
        mockMvc.perform(get(SHOPPING_CART_DETAIL_URL, shoppingCartId)
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(shoppingCart.getId().intValue())))
            .andExpect(jsonPath("$.total", is(shoppingCartService.calculateTotal(shoppingCart))));

        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCartId, user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }


    // ============================================================
    // Test update shopping cart
    // ============================================================

    /**
     * Test update shopping cart fail forbidden exception for admin user
     */
    @Test
    public void testUpdateShoppingCartFailForbiddenForAdminUser() throws Exception {
        // Generate token have role admin
        User user = fakerService.fakeAdminUser();
        String token = jwtTokenService.createToken(user);

        // Mock shopping cart request
        ShoppingCartRequest request = fakerService.fakeShoppingCartRequest();

        // Call api
        mockMvc.perform(put(SHOPPING_CART_DETAIL_URL, faker.number().randomNumber())
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isForbidden());
    }

    /**
     * Test update shopping cart fail resource not found when shopping cart id does not exist
     */
    @Test
    public void testUpdateShoppingCartFailResourceNotFoundWhenShoppingCartIdDoesNotExist() throws Exception {
        // Mock shopping cart
        long id = 1L;
        ShoppingCartRequest request = fakerService.fakeShoppingCartRequest();

        // Generate token have role member
        User user = fakerService.fakeMemberUser();
        String token = jwtTokenService.createToken(user);

        // Mock method
        when(shoppingCartRepository.findOne(id, user.getId())).thenReturn(null);

        // Call api
        mockMvc.perform(put(SHOPPING_CART_DETAIL_URL, id)
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isNotFound());

        verify(shoppingCartRepository, times(1)).findOne(id, user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test update shopping cart fail resource exists when shopping cart name belong to other shopping cart
     */
    @Test
    public void testUpdateShoppingCartFailResourceExistsWhenShoppingCartNameExistsInOtherShoppingCart()
        throws Exception {

        User user = fakerService.fakeMemberUser();
        String token = jwtTokenService.createToken(user);

        // Mock shopping cart and shopping cart request
        ShoppingCartRequest request = fakerService.fakeShoppingCartRequest();
        ShoppingCart shoppingCart = fakerService.fakeShoppingCart();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), user.getId())).thenReturn(shoppingCart);
        when(shoppingCartRepository.existsByName(request.getName())).thenReturn(true);

        // Call api
        mockMvc.perform(put(SHOPPING_CART_DETAIL_URL, shoppingCart.getId())
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isConflict());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), user.getId());
        verify(shoppingCartRepository, times(1)).existsByName(request.getName());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test update shopping cart success when shopping cart name not change
     */
    @Test
    public void testUpdateShoppingCartSuccessWhenShoppingCartNameNotChange() throws Exception {

        User user = fakerService.fakeMemberUser();
        String token = jwtTokenService.createToken(user);

        // Mock shopping cart
        ShoppingCart shoppingCart = fakerService.fakeShoppingCart();
        ShoppingCartRequest request = shoppingCartMapper.toShoppingCartRequest(shoppingCart);

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), user.getId())).thenReturn(shoppingCart);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(shoppingCart);

        // Call api
        mockMvc.perform(put(SHOPPING_CART_DETAIL_URL, shoppingCart.getId())
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(shoppingCart.getName())));

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), user.getId());
        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test update shopping cart success when shopping cart name does not exist
     */
    @Test
    public void testUpdateShoppingCartSuccessWhenShoppingCartNameDoseNotExist() throws Exception {

        User user = fakerService.fakeMemberUser();
        String token = jwtTokenService.createToken(user);

        // Mock shopping cart
        ShoppingCart shoppingCart = fakerService.fakeShoppingCart();
        ShoppingCartRequest request = fakerService.fakeShoppingCartRequest();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), user.getId())).thenReturn(shoppingCart);
        when(shoppingCartRepository.existsByName(request.getName())).thenReturn(false);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(shoppingCart);

        // Call api
        mockMvc.perform(put(SHOPPING_CART_DETAIL_URL, shoppingCart.getId())
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(shoppingCart.getName())));

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), user.getId());
        verify(shoppingCartRepository, times(1)).existsByName(request.getName());
        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    // ============================================================
    // Test delete shopping cart
    // ============================================================

    /**
     * Test delete shopping cart fail forbidden exception for admin user
     */
    @Test
    public void testDeleteShoppingCartFailForbiddenForAdminUser() throws Exception {
        // Generate token have role admin
        User user = fakerService.fakeAdminUser();
        String token = jwtTokenService.createToken(user);

        // Call api
        mockMvc.perform(put(SHOPPING_CART_DETAIL_URL, faker.number().randomNumber())
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isForbidden());
    }

    /**
     * Test delete shopping cart fail resource not found when shopping cart id does not exist
     */
    @Test
    public void testDeleteShoppingCartFailResourceNotFoundWhenShoppingCartIdDoesNotExist() throws Exception {
        // Mock shopping cart
        long id = faker.number().randomNumber();
        ShoppingCartRequest request = fakerService.fakeShoppingCartRequest();

        // Generate token have role member
        User user = fakerService.fakeMemberUser();
        String token = jwtTokenService.createToken(user);

        // Mock method
        when(shoppingCartRepository.findOne(id, user.getId())).thenReturn(null);

        // Call api
        mockMvc.perform(delete(SHOPPING_CART_DETAIL_URL, id)
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isNotFound());

        verify(shoppingCartRepository, times(1)).findOne(id, user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test delete shopping cart success when authenticated user own shopping cart with given id
     */
    @Test
    public void testDeleteShoppingCartSuccess() throws Exception {
        // Generate token have role member
        User user = fakerService.fakeMemberUser();
        String token = jwtTokenService.createToken(user);

        // Mock shopping cart
        ShoppingCart shoppingCart = fakerService.fakeShoppingCart();
        ShoppingCartRequest request = shoppingCartMapper.toShoppingCartRequest(shoppingCart);

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), user.getId())).thenReturn(shoppingCart);
        doNothing().when(shoppingCartRepository).delete(shoppingCart);

        // Call api
        mockMvc.perform(delete(SHOPPING_CART_DETAIL_URL, shoppingCart.getId())
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isOk());

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), user.getId());
        verify(shoppingCartRepository, times(1)).delete(shoppingCart);
        verifyNoMoreInteractions(shoppingCartRepository);
    }


    // ============================================================
    // Test checkout shopping cart
    // ============================================================

    /**
     * Test checkout shopping cart throw forbidden exception for admin user
     */
    @Test
    public void testCheckoutShoppingCartThrowForbiddenExceptionForAdminUser() throws Exception {
        // Generate token have role admin
        User user = fakerService.fakeAdminUser();
        String token = jwtTokenService.createToken(user);

        // Call api
        mockMvc.perform(post(SHOPPING_CART_CHECKOUT_URL, faker.number().randomNumber())
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isForbidden());
    }

    /**
     * Test checkout shopping cart throw resource not found exception when shopping cart not exist
     */
    @Test
    public void testCheckoutShoppingCartThrowResourceNotFoundExceptionWhenShoppingCartNotExist() throws Exception {
        // Mock member user
        User user = fakerService.fakeMemberUser();

        // Fake token
        String token = jwtTokenService.createToken(user);

        // Mock data
        Long shoppingCartId = faker.number().randomNumber();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCartId, user.getId())).thenReturn(null);

        // Call api
        mockMvc.perform(post(SHOPPING_CART_CHECKOUT_URL, shoppingCartId)
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(SHOPPING_CART_NOT_FOUND.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCartId, user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test checkout shopping cart throw bad request exception when shopping cart done
     */
    @Test
    public void testCheckoutShoppingCartThrowBadRequestExceptionWhenShoppingCartDone() throws Exception {
        // Mock member user
        User user = fakerService.fakeMemberUser();

        // Fake token
        String token = jwtTokenService.createToken(user);

        // Mock data
        ShoppingCart shoppingCart = fakerService.fakeShoppingCart(ShoppingCartStatus.DONE);

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), user.getId())).thenReturn(shoppingCart);

        // Call api
        mockMvc.perform(post(SHOPPING_CART_CHECKOUT_URL, shoppingCart.getId())
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", is(SHOPPING_CART_DONE.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCart.getId(), user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test checkout shopping cart throw bad request exception when shopping cart empty
     */
    @Test
    public void testCheckoutShoppingCartThrowBadRequestExceptionWhenShoppingCartEmpty() throws Exception {
        // Mock member user
        User user = fakerService.fakeMemberUser();

        // Fake token
        String token = jwtTokenService.createToken(user);

        // Mock data
        ShoppingCart shoppingCart = fakerService.fakeShoppingCart(ShoppingCartStatus.EMPTY);

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), user.getId())).thenReturn(shoppingCart);

        // Call api
        mockMvc.perform(post(SHOPPING_CART_CHECKOUT_URL, shoppingCart.getId())
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", is(SHOPPING_CART_EMPTY.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCart.getId(), user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test checkout shopping cart success with status IN_PROGRESS
     */
    @Test
    public void testCheckoutShoppingCartSuccess() throws Exception {
        // Mock member user
        User user = fakerService.fakeMemberUser();

        // Fake token
        String token = jwtTokenService.createToken(user);

        // Mock data
        ShoppingCart shoppingCart = fakerService.fakeShoppingCart(ShoppingCartStatus.IN_PROGRESS);

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), user.getId())).thenReturn(shoppingCart);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(shoppingCart);

        // Call api
        mockMvc.perform(post(SHOPPING_CART_CHECKOUT_URL, shoppingCart.getId())
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isOk());

        assertEquals(shoppingCart.getStatus(), ShoppingCartStatus.DONE.getName());
        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCart.getId(), user.getId());
        verify(shoppingCartRepository, times(1))
            .save(any(ShoppingCart.class));
        verifyNoMoreInteractions(shoppingCartRepository);
    }

}
