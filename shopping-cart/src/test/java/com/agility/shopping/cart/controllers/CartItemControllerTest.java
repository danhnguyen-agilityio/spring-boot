package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.configs.SecurityConfig;
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
import com.agility.shopping.cart.utils.FakerService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.agility.shopping.cart.configs.WebSecurityConfig.CART_ITEM_DETAIL_URL;
import static com.agility.shopping.cart.configs.WebSecurityConfig.CART_ITEM_URL;
import static com.agility.shopping.cart.exceptions.CustomError.*;
import static com.agility.shopping.cart.utils.ConvertUtil.convertObjectToJsonBytes;
import static com.agility.shopping.cart.utils.FakerService.*;
import static org.hamcrest.CoreMatchers.is;
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
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();
    }

    // =================================================================================
    // Test Create Cart Item
    // =================================================================================

    /**
     * Test create cart item throw forbidden exception for admin user
     */
    @Test
    public void testCreateCartItem_ShouldThrowForbidden_WhenAdminUserAccess() throws Exception {
        testResponseData(post(CART_ITEM_URL), adminToken, null, HttpStatus.FORBIDDEN, null);
    }

    /**
     * Test response data for API request
     *
     * @param request    API Request
     * @param token      Token request
     * @param body       Body request
     * @param httpStatus Expected response status
     */
    private void testResponseData(MockHttpServletRequestBuilder request,
                                  String token,
                                  Object body,
                                  HttpStatus httpStatus,
                                  Map<String, Object> jsonMap
    ) throws Exception {
        ResultActions resultActions = mockMvc.perform(request
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(body)))
            .andDo(print())
            .andExpect(status().is(httpStatus.value()));

        if (jsonMap != null) {
            for (val entry : jsonMap.entrySet()) {
                resultActions.andExpect(jsonPath(entry.getKey(), is(entry.getValue())));
            }
        }

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

    // ===================================================================
    // Test Get All Cart Item By Shopping Cart Id
    // ===================================================================

    /**
     * Test find all cart item throw forbidden exception for admin user
     */
    @Test
    public void testFindAllCartItemThrowForbiddenExceptionForAdminUser() throws Exception {
        // Generate token have role admin
        User user = fakeAdminUser();
        String token = tokenAuthenticationService.createToken(user);

        // Call api
        mockMvc.perform(get(CART_ITEM_URL)
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isForbidden());
    }

    /**
     * Test find all cart item by given shopping cart id throw resource not found exception
     * when no shopping cart with given id of authenticated user
     */
    @Test
    public void testFindAllCartItemThrowResourceNotFoundExceptionWhenNoShoppingCartWithGivenIdOfAuthenticatedUser()
        throws Exception {

        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = tokenAuthenticationService.createToken(user);

        // Mock shopping cart id
        Long shoppingCartId = faker.number().randomNumber();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCartId, user.getId())).thenReturn(null);

        // Call api
        mockMvc.perform(get(CART_ITEM_URL)
            .header(securityConfig.getHeaderString(), token)
            .param("shoppingCartId", shoppingCartId.toString()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(SHOPPING_CART_NOT_FOUND.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCartId, user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test find all cart item success
     */
    @Test
    public void testFindAllCartItemSuccess() throws Exception {

        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = tokenAuthenticationService.createToken(user);

        // Mock shopping cart id
        ShoppingCart shoppingCart = fakeShoppingCart(user);

        // Mock list cart item
        List<CartItem> cartItems = fakeListCartItem();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), user.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findAllByShoppingCartId(shoppingCart.getId())).thenReturn(cartItems);

        // Call api
        mockMvc.perform(get(CART_ITEM_URL)
            .header(securityConfig.getHeaderString(), token)
            .param("shoppingCartId", shoppingCart.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].quantity", is(cartItems.get(0).getQuantity())));

        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCart.getId(), user.getId());
        verify(cartItemRepository, times(1))
            .findAllByShoppingCartId(shoppingCart.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
        verifyNoMoreInteractions(cartItemRepository);
    }

    // =========================================================
    // Test Get One Cart Item By Cart Item Id And Shopping Cart Id
    // =========================================================

    /**
     * Test find one cart item throw forbidden exception for admin user
     */
    @Test
    public void testFindOneCartItemThrowForbiddenExceptionForAdminUser() throws Exception {
        // Generate token have role admin
        User user = fakeAdminUser();
        String token = tokenAuthenticationService.createToken(user);

        // Call api
        mockMvc.perform(get(CART_ITEM_DETAIL_URL, faker.number().randomNumber())
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isForbidden());
    }

    /**
     * Test find one cart item by given cart item id and shopping cart id throw resource not found exception
     * when no shopping cart with given id of authenticated user
     */
    @Test
    public void testFindOneCartItemThrowResourceNotFoundExceptionWhenNoShoppingCartWithGivenIdOfAuthenticatedUser()
        throws Exception {

        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = tokenAuthenticationService.createToken(user);

        // Mock cart item id and shopping cart id
        Long cartItemId = faker.number().randomNumber();
        Long shoppingCartId = faker.number().randomNumber();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCartId, user.getId())).thenReturn(null);

        // Call api
        mockMvc.perform(get(CART_ITEM_DETAIL_URL, cartItemId)
            .header(securityConfig.getHeaderString(), token)
            .param("shoppingCartId", shoppingCartId.toString()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(SHOPPING_CART_NOT_FOUND.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCartId, user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test find one cart item throw resource not found exception
     * when no cart item with given cart item id and shopping cart id
     */
    @Test
    public void testFindOneCartItemThrowResourceNotFoundExceptionWhenNoCartItemWithGivenCartItemIdAndShoppingCartId()
        throws Exception {

        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = tokenAuthenticationService.createToken(user);

        // Mock shopping cart and cart item
        ShoppingCart shoppingCart = fakeShoppingCart(user);
        CartItem cartItem = fakeCartItem();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), user.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId()))
            .thenReturn(null);

        // Call api
        mockMvc.perform(get(CART_ITEM_DETAIL_URL, cartItem.getId())
            .header(securityConfig.getHeaderString(), token)
            .param("shoppingCartId", shoppingCart.getId().toString()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(CART_ITEM_NOT_FOUND.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCart.getId(), user.getId());
        verify(cartItemRepository, times(1)).
            findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
        verifyNoMoreInteractions(cartItemRepository);
    }

    /**
     * Test fine one cart item success
     */
    @Test
    public void testFindOneCartItemSuccess() throws Exception {

        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = tokenAuthenticationService.createToken(user);

        // Mock shopping cart and cart item
        ShoppingCart shoppingCart = fakeShoppingCart(user);
        CartItem cartItem = fakeCartItem();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), user.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId()))
            .thenReturn(cartItem);

        // Call api
        mockMvc.perform(get(CART_ITEM_DETAIL_URL, cartItem.getId())
            .header(securityConfig.getHeaderString(), token)
            .param("shoppingCartId", shoppingCart.getId().toString()))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.product.id", is(cartItem.getProduct().getId().intValue())))
            .andExpect(jsonPath("$.shoppingCart.id", is(cartItem.getShoppingCart().getId().intValue())))
            .andExpect(jsonPath("$.quantity", is(cartItem.getQuantity())));


        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCart.getId(), user.getId());
        verify(cartItemRepository, times(1)).
            findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
        verifyNoMoreInteractions(cartItemRepository);
    }

    // =========================================================
    // Test Update Cart Item
    // =========================================================

    /**
     * Test update cart item throw forbidden exception for admin user
     */
    @Test
    public void testUpdateCartItemThrowForbiddenExceptionForAdminUser() throws Exception {
        // Generate token have role admin
        User user = fakeAdminUser();
        String token = tokenAuthenticationService.createToken(user);

        // Call api
        mockMvc.perform(put(CART_ITEM_DETAIL_URL, faker.number().randomNumber())
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isForbidden());
    }

    /**
     * Test update cart item throw resource not found exception
     * when no shopping cart with given id of authenticated user
     */
    @Test
    public void testUpdateCartItemThrowResourceNotFoundExceptionWhenNoShoppingCartWithGivenIdOfAuthenticatedUser()
        throws Exception {

        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = tokenAuthenticationService.createToken(user);

        // Mock data
        CartItemUpdate cartItemUpdate = fakeCartItemUpdate();
        CartItem cartItem = fakeCartItem();

        // Mock method
        when(shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), user.getId())).thenReturn(null);

        // Call api
        mockMvc.perform(put(CART_ITEM_DETAIL_URL, cartItem.getId())
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(cartItemUpdate)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(SHOPPING_CART_NOT_FOUND.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(cartItemUpdate.getShoppingCartId(), user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test update cart item throw resource not found exception when shopping cart done
     */
    @Test
    public void testUpdateCartItemThrowBadRequestExceptionWhenShoppingCartDone() throws Exception {
        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = tokenAuthenticationService.createToken(user);

        // Mock data
        CartItemUpdate cartItemUpdate = fakeCartItemUpdate();
        CartItem cartItem = fakeCartItem();
        ShoppingCart shoppingCart = fakeShoppingCart(ShoppingCartStatus.DONE);

        // Mock method
        when(shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), user.getId())).thenReturn(shoppingCart);

        // Call api
        mockMvc.perform(put(CART_ITEM_DETAIL_URL, cartItem.getId())
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(cartItemUpdate)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", is(SHOPPING_CART_DONE.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(cartItemUpdate.getShoppingCartId(), user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test update cart item throw resource not found exception
     * when no cart item with given cart item id and shopping cart id
     */
    @Test
    public void testUpdateCartItemThrowResourceNotFoundExceptionWhenNoCartItemWithGivenCartItemIdAndShoppingCartId()
        throws Exception {

        // Mock member user
        User user = fakeMemberUser();
        String token = tokenAuthenticationService.createToken(user);

        // Mock data
        ShoppingCart shoppingCart = fakeShoppingCart(user);
        shoppingCart.setStatus(ShoppingCartStatus.IN_PROGRESS.getName());
        CartItemUpdate cartItemUpdate = fakeCartItemUpdate();
        cartItemUpdate.setShoppingCartId(shoppingCart.getId());
        CartItem cartItem = fakeCartItem();
        cartItem.setShoppingCart(shoppingCart);

        // Mock method
        when(shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), user.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), cartItemUpdate.getShoppingCartId()))
            .thenReturn(null);

        // Call api
        mockMvc.perform(put(CART_ITEM_DETAIL_URL, cartItem.getId())
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(cartItemUpdate)))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(CART_ITEM_NOT_FOUND.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(cartItemUpdate.getShoppingCartId(), user.getId());
        verify(cartItemRepository, times(1)).
            findOneByCartItemIdAndShoppingCartId(cartItem.getId(), cartItemUpdate.getShoppingCartId());
        verifyNoMoreInteractions(shoppingCartRepository);
        verifyNoMoreInteractions(cartItemRepository);
    }

    /**
     * Test update cart item success
     */
    @Test
    public void testUpdateCartItemSuccess() throws Exception {

        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = tokenAuthenticationService.createToken(user);

        // Mock data
        ShoppingCart shoppingCart = fakeShoppingCart(user);
        CartItemUpdate cartItemUpdate = fakeCartItemUpdate();
        cartItemUpdate.setShoppingCartId(shoppingCart.getId());
        CartItem cartItem = fakeCartItem();
        cartItem.setShoppingCart(shoppingCart);

        // Mock method
        when(shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), user.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), cartItemUpdate.getShoppingCartId()))
            .thenReturn(cartItem);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        // Call api
        mockMvc.perform(put(CART_ITEM_DETAIL_URL, cartItem.getId())
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(cartItemUpdate)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.product.id", is(cartItem.getProduct().getId().intValue())))
            .andExpect(jsonPath("$.shoppingCart.id", is(cartItem.getShoppingCart().getId().intValue())))
            .andExpect(jsonPath("$.quantity", is(cartItemUpdate.getQuantity())));

        verify(shoppingCartRepository, times(1)).
            findOne(cartItemUpdate.getShoppingCartId(), user.getId());
        verify(cartItemRepository, times(1)).
            findOneByCartItemIdAndShoppingCartId(cartItem.getId(), cartItemUpdate.getShoppingCartId());
        verify(cartItemRepository, times(1))
            .save(any(CartItem.class));
        verifyNoMoreInteractions(shoppingCartRepository);
        verifyNoMoreInteractions(cartItemRepository);
    }

    // =========================================================
    // Test Delete Cart Item
    // =========================================================

    /**
     * Test delete cart item throw forbidden exception for admin user
     */
    @Test
    public void testDeleteCartItemThrowForbiddenExceptionForAdminUser() throws Exception {
        // Generate token have role admin
        User user = fakeAdminUser();
        String token = tokenAuthenticationService.createToken(user);

        // Call api
        mockMvc.perform(delete(CART_ITEM_DETAIL_URL, faker.number().randomNumber())
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isForbidden());
    }

    /**
     * Test delete cart item throw resource not found exception
     * when no shopping cart with given id of authenticated user
     */
    @Test
    public void testDeleteCartItemThrowResourceNotFoundExceptionWhenNoShoppingCartWithGivenIdOfAuthenticatedUser()
        throws Exception {

        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = tokenAuthenticationService.createToken(user);

        // Mock data
        Long shoppingCartId = faker.number().randomNumber();
        Long cartItemId = faker.number().randomNumber();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCartId, user.getId())).thenReturn(null);

        // Call api
        mockMvc.perform(delete(CART_ITEM_DETAIL_URL, cartItemId)
            .header(securityConfig.getHeaderString(), token)
            .param("shoppingCartId", shoppingCartId.toString()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(SHOPPING_CART_NOT_FOUND.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCartId, user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test delete cart item throw resource not found exception
     * when no cart item with given cart item id and shopping cart id
     */
    @Test
    public void testDeleteCartItemThrowResourceNotFoundExceptionWhenNoCartItemWithGivenCartItemIdAndShoppingCartId()
        throws Exception {

        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = tokenAuthenticationService.createToken(user);

        // Mock data
        Long shoppingCartId = faker.number().randomNumber();
        Long cartItemId = faker.number().randomNumber();
        ShoppingCart shoppingCart = fakeShoppingCart(user);
        shoppingCart.setId(shoppingCartId);

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCartId, user.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItemId, shoppingCartId))
            .thenReturn(null);

        // Call api
        mockMvc.perform(delete(CART_ITEM_DETAIL_URL, cartItemId)
            .header(securityConfig.getHeaderString(), token)
            .param("shoppingCartId", shoppingCartId.toString()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(CART_ITEM_NOT_FOUND.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCartId, user.getId());
        verify(cartItemRepository, times(1)).
            findOneByCartItemIdAndShoppingCartId(cartItemId, shoppingCartId);
        verifyNoMoreInteractions(shoppingCartRepository);
        verifyNoMoreInteractions(cartItemRepository);
    }

    /**
     * Test delete cart item success
     */
    @Test
    public void testDeleteCartItemSuccess() throws Exception {

        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = tokenAuthenticationService.createToken(user);

        // Mock data
        Long shoppingCartId = faker.number().randomNumber();
        Long cartItemId = faker.number().randomNumber();
        ShoppingCart shoppingCart = fakeShoppingCart(ShoppingCartStatus.IN_PROGRESS);
        shoppingCart.setId(shoppingCartId);
        CartItem cartItem = fakeCartItem();
        cartItem.setId(cartItemId);
        cartItem.setShoppingCart(shoppingCart);
        shoppingCart.setCartItems(Sets.newSet(cartItem));

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCartId, user.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItemId, shoppingCartId))
            .thenReturn(cartItem);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(shoppingCart);

        // Call api
        mockMvc.perform(delete(CART_ITEM_DETAIL_URL, cartItem.getId())
            .header(securityConfig.getHeaderString(), token)
            .param("shoppingCartId", shoppingCartId.toString()))
            .andExpect(status().isOk());

        assertEquals(shoppingCart.getStatus(), ShoppingCartStatus.EMPTY.getName());
        verify(shoppingCartRepository, times(1)).
            findOne(shoppingCartId, user.getId());
        verify(cartItemRepository, times(1)).
            findOneByCartItemIdAndShoppingCartId(cartItemId, shoppingCartId);
        verify(shoppingCartRepository, times(1))
            .save(any(ShoppingCart.class));
        verifyNoMoreInteractions(shoppingCartRepository);
        verifyNoMoreInteractions(cartItemRepository);
    }

}
