package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.configs.SecurityConfig;
import com.agility.shopping.cart.constants.MessageConstant;
import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.dto.CartItemRequest;
import com.agility.shopping.cart.dto.CartItemUpdate;
import com.agility.shopping.cart.exceptions.CustomError;
import com.agility.shopping.cart.mappers.CartItemMapper;
import com.agility.shopping.cart.models.CartItem;
import com.agility.shopping.cart.models.Product;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.repositories.CartItemRepository;
import com.agility.shopping.cart.repositories.ProductRepository;
import com.agility.shopping.cart.repositories.ShoppingCartRepository;
import com.agility.shopping.cart.services.TokenAuthenticationService;
import com.agility.shopping.cart.services.FakerService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.agility.shopping.cart.configs.WebSecurityConfig.CART_ITEM_DETAIL_URL;
import static com.agility.shopping.cart.configs.WebSecurityConfig.CART_ITEM_URL;
import static com.agility.shopping.cart.exceptions.CustomError.*;
import static com.agility.shopping.cart.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class test RESTful api for cart item
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CartItemControllerTest {

    private static final Faker faker = new Faker();
    private User memberUser;
    private User adminUser;
    private String memberToken;
    private String adminToken;
    private Product product;
    private ShoppingCart shoppingCart;
    private CartItemRequest cartItemRequest;
    private CartItem cartItem;
    private CartItemUpdate cartItemUpdate;
    private List<CartItem> cartItems;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private FakerService fakerService;

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CartItemRepository cartItemRepository;

    @Before
    public void setUp() {
        memberUser = fakerService.fakeUser(RoleType.MEMBER);
        adminUser = fakerService.fakeUser(RoleType.ADMIN);
        memberToken = tokenAuthenticationService.createToken(memberUser);
        adminToken = tokenAuthenticationService.createToken(adminUser);
        product = fakerService.fakeProduct();
        shoppingCart = fakerService.fakeShoppingCart();
        cartItem = fakerService.fakeCartItem();
        cartItemRequest = fakerService.fakeCartItemRequest();
        cartItems = fakerService.fakeListCartItem(3);
        cartItemUpdate = fakerService.fakeCartItemUpdate();
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();
    }

    // =================================================================
    // Test Create Cart Item
    // =================================================================

    /**
     * Test create cart item throw forbidden exception for admin user
     */
    @Test
    public void testCreateCartItem_ShouldThrowForbidden_WhenAdminUserAccess() throws Exception {
        testResponseData(post(CART_ITEM_URL), adminToken, null, HttpStatus.FORBIDDEN, null);
    }

    /**
     * Test create cart item throw resource not found exception when shopping cart not found
     */
    @Test
    public void testCreateCartItem_ShouldThrowResourceNotFound_WhenShoppingCartNotFound()
        throws Exception {
        when(shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), memberUser.getId())).thenReturn(null);

        testResponseData(post(CART_ITEM_URL), memberToken, cartItemRequest, HttpStatus.NOT_FOUND,
            createJsonMapError(SHOPPING_CART_NOT_FOUND));

        verify(shoppingCartRepository, times(1))
            .findOne(cartItemRequest.getShoppingCartId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test create cart item throw bad request exception when shopping cart done
     */
    @Test
    public void testCreatedCartItem_ShouldThrowBadRequestException_WhenShoppingCartDone() throws Exception {
        shoppingCart.setStatus(ShoppingCartStatus.DONE.name());
        when(shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), memberUser.getId()))
            .thenReturn(shoppingCart);

        testResponseData(post(CART_ITEM_URL), memberToken, cartItemRequest, HttpStatus.BAD_REQUEST,
            createJsonMapError(SHOPPING_CART_DONE));

        verify(shoppingCartRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test create cart item throw resource not found exception when product not found
     */
    @Test
    public void testCreateCartItem_ShouldThrowResourceNotFound_WhenProductNotFound() throws Exception {
        shoppingCart.setStatus(ShoppingCartStatus.IN_PROGRESS.name());
        when(shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), memberUser.getId())).
            thenReturn(shoppingCart);
        when(productRepository.findOne(cartItemRequest.getProductId())).thenReturn(null);

        testResponseData(post(CART_ITEM_URL), memberToken, cartItemRequest, HttpStatus.NOT_FOUND,
            createJsonMapError(PRODUCT_NOT_FOUND));

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
    public void testCreateCartItem_ShouldSuccess_WhenCartItemNotFound() throws Exception {
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
        testResponseData(post(CART_ITEM_URL), memberToken, cartItemRequest, HttpStatus.OK, jsonMap);

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
    public void testCreateCartItem_ShouldSuccess_WhenCartItemFound() throws Exception {
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
        testResponseData(post(CART_ITEM_URL), memberToken, cartItemRequest, HttpStatus.OK, jsonMap);

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
    public void testFindAllCartItem_ShouldThrowForbidden_WhenAdminUserAccess() throws Exception {
        testResponseData(get(CART_ITEM_URL), adminToken, null, HttpStatus.FORBIDDEN, null);
    }

    /**
     * Test find all cart item should throw resource not found exception when shopping cart not found
     */
    @Test
    public void testFindAllCartItem_ShouldThrowResourceNotFound_WhenShoppingCartNotFound()
        throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(null);

        // Test response data for request
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());
        testResponseData(get(CART_ITEM_URL), memberToken, null, HttpStatus.NOT_FOUND,
            createJsonMapError(SHOPPING_CART_NOT_FOUND), params);

        // Verify called method
        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCart.getId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test find all cart item success
     */
    @Test
    public void testFindAllCartItem_ShouldSuccess() throws Exception {
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
        testResponseData(get(CART_ITEM_URL), memberToken, null, HttpStatus.OK,
            jsonPath, params);

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
    public void testFindOneCartItem_ShouldThrowForbidden_WhenAdminUserAccess() throws Exception {
        testResponseData(get(CART_ITEM_DETAIL_URL, faker.number().randomNumber()), adminToken, null,
            HttpStatus.FORBIDDEN, null);
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
        testResponseData(get(CART_ITEM_DETAIL_URL, cartItem.getId()), memberToken, null, HttpStatus.NOT_FOUND,
            createJsonMapError(SHOPPING_CART_NOT_FOUND), params);

        // Verify called method
        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCart.getId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test find one cart item throw resource not found exception when cart item not found
     */
    @Test
    public void testFindOneCartItem_ShouldThrowResourceNotFound_WhenCartItemNotFound()
        throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId()))
            .thenReturn(null);

        // Test response data for request
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());
        testResponseData(get(CART_ITEM_DETAIL_URL, cartItem.getId()), memberToken, null, HttpStatus.NOT_FOUND,
            createJsonMapError(CART_ITEM_NOT_FOUND), params);

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
    public void testFindOneCartItem_ShouldSuccess() throws Exception {
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
        testResponseData(get(CART_ITEM_DETAIL_URL, cartItem.getId()), memberToken, null, HttpStatus.OK,
            jsonMap, params);

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
    public void testUpdateCartItem_ShouldThrowForbidden_WhenAdminUserAccess() throws Exception {
        // Test response data for request
        testResponseData(put(CART_ITEM_DETAIL_URL, cartItem.getId()), adminToken, cartItemRequest,
            HttpStatus.FORBIDDEN, null);
    }

    /**
     * Test update cart item throw resource not found exception when shopping cart not found
     */
    @Test
    public void testUpdateCartItem_ShouldThrowResourceNotFound_WhenShoppingCartNotFound()
        throws Exception {
        log.debug("Test update cart item success");
        log.debug("Cart item update: {}", cartItemUpdate);

        // Mock method
        when(shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), memberUser.getId())).thenReturn(null);

        // Test response data for request
        testResponseData(put(CART_ITEM_DETAIL_URL, cartItem.getId()), memberToken, cartItemUpdate,
            HttpStatus.NOT_FOUND, createJsonMapError(SHOPPING_CART_NOT_FOUND));

        // Verify called method
        verify(shoppingCartRepository, times(1)).
            findOne(cartItemUpdate.getShoppingCartId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test update cart item throw resource not found exception when shopping cart done
     */
    @Test
    public void testUpdateCartItem_ShouldThrowBadRequest_WhenShoppingCartDone() throws Exception {
        // Mock data and method
        shoppingCart.setStatus(ShoppingCartStatus.DONE.getName());
        when(shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), memberUser.getId()))
            .thenReturn(shoppingCart);

        // Test response data for request
        testResponseData(put(CART_ITEM_DETAIL_URL, cartItem.getId()), memberToken, cartItemUpdate,
            HttpStatus.BAD_REQUEST, createJsonMapError(SHOPPING_CART_DONE));

        // Verify called method
        verify(shoppingCartRepository, times(1)).
            findOne(cartItemUpdate.getShoppingCartId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test update cart item throw resource not found exception when cart item not found
     */
    @Test
    public void testUpdateCartItem_ShouldThrowResourceNotFoundE_WhenCartItemNotFound()
        throws Exception {
        // Mock method and data
        shoppingCart.setStatus(ShoppingCartStatus.IN_PROGRESS.getName());
        when(shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), memberUser.getId()))
            .thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), cartItemUpdate.getShoppingCartId()))
            .thenReturn(null);

        // Test response data for request
        testResponseData(put(CART_ITEM_DETAIL_URL, cartItem.getId()), memberToken, cartItemUpdate,
            HttpStatus.NOT_FOUND, createJsonMapError(CART_ITEM_NOT_FOUND));

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
    public void testUpdateCartItem_ShouldSuccess() throws Exception {
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
        testResponseData(put(CART_ITEM_DETAIL_URL, cartItem.getId()), memberToken, cartItemUpdate,
            HttpStatus.OK, jsonMap);

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
     * Test delete cart item throw forbidden exception for admin user
     */
    @Test
    public void testDeleteCartItemThrowForbiddenExceptionForAdminUser() throws Exception {
        testResponseData(delete(CART_ITEM_DETAIL_URL, cartItem.getId()), adminToken, null, HttpStatus.FORBIDDEN, null);
    }

    /**
     * Test delete cart item throw resource not found exception when shopping cart not found
     */
    @Test
    public void testDeleteCartItem_ShouldThrowResourceNotFound_WhenShoppingCartNotFound() throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(null);

        // Test response data for request
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());
        testResponseData(delete(CART_ITEM_DETAIL_URL, cartItem.getId()), memberToken, null, HttpStatus.NOT_FOUND,
            createJsonMapError(SHOPPING_CART_NOT_FOUND), params);

        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test delete cart item throw resource not found exception
     * when no cart item with given cart item id and shopping cart id
     */
    @Test
    public void testDeleteCartItem_ShouldThrowResourceNotFound_WhenCartItemNotFound() throws Exception {
        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), memberUser.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId()))
            .thenReturn(null);

        // Test response data for request
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCartId", shoppingCart.getId());
        testResponseData(delete(CART_ITEM_DETAIL_URL, cartItem.getId()), memberToken, null, HttpStatus.NOT_FOUND,
            createJsonMapError(CART_ITEM_NOT_FOUND), params);

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
    public void testDeleteCartItem_ShouldSuccess() throws Exception {
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
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("$", MessageConstant.CART_ITEM_DELETE_SUCCESS);
        testResponseData(delete(CART_ITEM_DETAIL_URL, cartItem.getId()), memberToken, null, HttpStatus.OK, jsonMap, params);

        // Verify status shopping cart is EMPTY when no cart item for shopping cart after delete cart item
        assertEquals(shoppingCart.getStatus(), ShoppingCartStatus.EMPTY.getName());

        // Verify called method
        verify(shoppingCartRepository, times(1)).findOne(shoppingCart.getId(), memberUser.getId());
        verify(cartItemRepository, times(1)).
            findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId());
        verify(shoppingCartRepository, times(1)).save(shoppingCart);
        verifyNoMoreInteractions(shoppingCartRepository, cartItemRepository);
    }

    /**
     * Test response data for API request
     *
     * @param request    API Request
     * @param token      Token request
     * @param body       Body request
     * @param httpStatus Expected response status
     * @param jsonMap    Json map
     * @param params     Params map
     */
    private void testResponseData(MockHttpServletRequestBuilder request,
                                  String token,
                                  Object body,
                                  HttpStatus httpStatus,
                                  Map<String, Object> jsonMap,
                                  Map<String, Object> params) throws Exception {
        MultiValueMap<String, String> multiValueMapParam = new LinkedMultiValueMap<>();
        if (params != null) {
            for (val param : params.entrySet()) {
                multiValueMapParam.add(param.getKey(), param.getValue().toString());
            }
        }

        ResultActions resultActions = mockMvc.perform(request.params(multiValueMapParam)
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(body)))
            .andDo(print())
            .andExpect(status().is(httpStatus.value()));

        if (jsonMap != null) {
            for (val entry : jsonMap.entrySet()) {
                if (entry.getValue() instanceof Matcher) {
                    resultActions.andExpect(jsonPath(entry.getKey(), (Matcher) entry.getValue()));

                } else {
                    resultActions.andExpect(jsonPath(entry.getKey(), is(entry.getValue())));
                }

            }
        }
    }

    /**
     * Test response data for API request
     *
     * @param request    API Request
     * @param token      Token request
     * @param body       Body request
     * @param httpStatus Expected response status
     * @param jsonMap    Json map
     */
    private void testResponseData(MockHttpServletRequestBuilder request,
                                  String token,
                                  Object body,
                                  HttpStatus httpStatus,
                                  Map<String, Object> jsonMap) throws Exception {
        testResponseData(request, token, body, httpStatus, jsonMap, null);
    }

    /**
     * Create json map with custom error code
     *
     * @param customError Custom error contain error code
     * @return Json map contain error code
     */
    private Map<String, Object> createJsonMapError(CustomError customError) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("$.code", customError.code());
        return jsonMap;
    }
}
