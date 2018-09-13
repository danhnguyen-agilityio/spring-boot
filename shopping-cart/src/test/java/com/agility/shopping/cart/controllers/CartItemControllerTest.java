package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.dto.CartItemRequest;
import com.agility.shopping.cart.dto.CartItemUpdate;
import com.agility.shopping.cart.mappers.CartItemMapper;
import com.agility.shopping.cart.models.CartItem;
import com.agility.shopping.cart.models.Product;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.repositories.CartItemRepository;
import com.agility.shopping.cart.repositories.ProductRepository;
import com.agility.shopping.cart.repositories.ShoppingCartRepository;
import com.agility.shopping.cart.services.TokenAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.agility.shopping.cart.configs.WebSecurityConfig.CART_ITEM_DETAIL_URL;
import static com.agility.shopping.cart.configs.WebSecurityConfig.CART_ITEM_URL;
import static com.agility.shopping.cart.constants.SecurityConstants.HEADER_STRING;
import static com.agility.shopping.cart.exceptions.CustomError.*;
import static com.agility.shopping.cart.utils.ConvertUtil.convertObjectToJsonBytes;
import static com.agility.shopping.cart.utils.FakerUtil.*;
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
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private CartItemMapper cartItemMapper;

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CartItemRepository cartItemRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();
    }

    // =========================================================
    // Test Create Cart Item
    // =========================================================

    /**
     * Test create cart item throw forbidden exception for admin user
     */
    @Test
    public void testCreateCartItemThrowForbiddenExceptionForAdminUser() throws Exception {
        // Create admin token
        String token = generateAdminToken();

        // Call api
        mockMvc.perform(post(CART_ITEM_URL)
            .header(HEADER_STRING, token))
            .andExpect(status().isForbidden());
    }

    /**
     * Test create cart item throw resource not found exception
     * when no shopping cart by given shopping cart id and user id
     */
    @Test
    public void testCreateCartItemThrowResourceNotFoundExceptionWhenNoShoppingCartByGivenShoppingCartIdAndUserId()
        throws Exception {

        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = TokenAuthenticationService.createToken(user);

        // Mock cart item
        CartItemRequest cartItemRequest = fakeCartItemRequest();

        // Mock method
        when(shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), user.getId())).thenReturn(null);

        // Call api
        mockMvc.perform(post(CART_ITEM_URL)
            .header(HEADER_STRING, token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(cartItemRequest)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(SHOPPING_CART_NOT_FOUND.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test create cart item throw resource not found exception when shopping cart done
     */
    @Test
    public void testCreatedCartItemThrowBadRequestExceptionWhenShoppingCartDone() throws Exception {
        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = TokenAuthenticationService.createToken(user);

        // Mock cart item request and shopping cart with status DONE
        CartItemRequest cartItemRequest = fakeCartItemRequest();
        ShoppingCart shoppingCart = fakeShoppingCart(ShoppingCartStatus.DONE);

        // Mock method
        when(shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), user.getId())).thenReturn(shoppingCart);

        // Call api
        mockMvc.perform(post(CART_ITEM_URL)
            .header(HEADER_STRING, token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(cartItemRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", is(SHOPPING_CART_DONE.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test create cart item throw resource not found exception when product id not exist
     */
    @Test
    public void testCreateCartItemThrowResourceNotFoundExceptionWhenProductIdNotExist() throws Exception {
        // Mock member user
        User user = fakeMemberUser();

        // Fake token
        String token = TokenAuthenticationService.createToken(user);

        // Mock data
        CartItemRequest cartItemRequest = fakeCartItemRequest();
        ShoppingCart shoppingCart = fakeShoppingCart(ShoppingCartStatus.IN_PROGRESS);

        // Mock method
        when(shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), user.getId())).thenReturn(shoppingCart);
        when(productRepository.findOne(cartItemRequest.getProductId())).thenReturn(null);

        // Call api
        mockMvc.perform(post(CART_ITEM_URL)
            .header(HEADER_STRING, token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(cartItemRequest)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(PRODUCT_NOT_FOUND.code())));

        verify(shoppingCartRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), user.getId());
        verify(productRepository, times(1)).
            findOne(cartItemRequest.getProductId());
        verifyNoMoreInteractions(shoppingCartRepository);
        verifyNoMoreInteractions(productRepository);
    }

    /**
     * Test create cart item success when cart item with given shopping cart id and product id not exists
     */
    @Test
    public void testCreateCartItemSuccessWhenCartItemNotExist() throws Exception {
        // Mock member user
        User user = fakeMemberUser();

        // Generate token
        String token = TokenAuthenticationService.createToken(user);

        // Mock cart item request, shopping cart, product
        CartItemRequest cartItemRequest = fakeCartItemRequest();
        ShoppingCart shoppingCart = fakeShoppingCart(ShoppingCartStatus.IN_PROGRESS);
        Product product = fakeProduct();
        long quantity = generateLongNumber();
        CartItem cartItem = cartItemMapper.toCartItem(cartItemRequest);
        cartItem.setQuantity(quantity);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setProduct(product);

        // Mock method
        when(shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), user.getId())).thenReturn(shoppingCart);
        when(productRepository.findOne(cartItemRequest.getProductId())).thenReturn(product);
        when(cartItemRepository.findOne(cartItemRequest.getShoppingCartId(), cartItemRequest.getProductId()))
            .thenReturn(null);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        // Call api
        mockMvc.perform(post(CART_ITEM_URL)
            .header(HEADER_STRING, token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(cartItemRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.quantity", is(quantity)))
            .andExpect(jsonPath("$.shoppingCart.id", is(cartItem.getShoppingCart().getId())))
            .andExpect(jsonPath("$.product.id", is(cartItem.getProduct().getId())));

        verify(shoppingCartRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), user.getId());
        verify(productRepository, times(1)).
            findOne(cartItemRequest.getProductId());
        verify(cartItemRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), cartItemRequest.getProductId());
        verify(cartItemRepository, times(1)).
            save(any(CartItem.class));
        verifyNoMoreInteractions(shoppingCartRepository);
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(cartItemRepository);
    }

    /**
     * Test create cart item success when cart item with given shopping cart id and product id already exists
     */
    // FIXME:: Refactor test case
    @Test
    public void testCreateCartItemSuccessWhenCartItemExist() throws Exception {
        // Mock member user
        User user = fakeMemberUser();

        // Generate token
        String token = TokenAuthenticationService.createToken(user);

        // Mock cart item request, shopping cart, product
        CartItemRequest cartItemRequest = fakeCartItemRequest();
        ShoppingCart shoppingCart = fakeShoppingCart(ShoppingCartStatus.IN_PROGRESS);
        Product product = fakeProduct();
        long quantity = generateLongNumber();
        CartItem cartItem = cartItemMapper.toCartItem(cartItemRequest);
        cartItem.setQuantity(quantity);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setProduct(product);

        // Mock method
        when(shoppingCartRepository.findOne(cartItemRequest.getShoppingCartId(), user.getId())).thenReturn(shoppingCart);
        when(productRepository.findOne(cartItemRequest.getProductId())).thenReturn(product);
        when(cartItemRepository.findOne(cartItemRequest.getShoppingCartId(), cartItemRequest.getProductId()))
            .thenReturn(cartItem);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        // Call api
        mockMvc.perform(post(CART_ITEM_URL)
            .header(HEADER_STRING, token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(cartItemRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.quantity", is(quantity + cartItemRequest.getQuantity())))
            .andExpect(jsonPath("$.shoppingCart.id", is(cartItem.getShoppingCart().getId())))
            .andExpect(jsonPath("$.product.id", is(cartItem.getProduct().getId())));

        verify(shoppingCartRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), user.getId());
        verify(productRepository, times(1)).
            findOne(cartItemRequest.getProductId());
        verify(cartItemRepository, times(1)).
            findOne(cartItemRequest.getShoppingCartId(), cartItemRequest.getProductId());
        verify(cartItemRepository, times(1)).
            save(any(CartItem.class));
        verifyNoMoreInteractions(shoppingCartRepository);
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(cartItemRepository);
    }

    // =========================================================
    // Test Get All Cart Item By Shopping Cart Id
    // =========================================================

    /**
     * Test find all cart item throw forbidden exception for admin user
     */
    @Test
    public void testFindAllCartItemThrowForbiddenExceptionForAdminUser() throws Exception {
        // Create admin token
        String token = generateAdminToken();

        // Call api
        mockMvc.perform(get(CART_ITEM_URL)
            .header(HEADER_STRING, token))
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
        String token = TokenAuthenticationService.createToken(user);

        // Mock shopping cart id
        Long shoppingCartId = generateLongNumber();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCartId, user.getId())).thenReturn(null);

        // Call api
        mockMvc.perform(get(CART_ITEM_URL)
            .header(HEADER_STRING, token)
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
        String token = TokenAuthenticationService.createToken(user);

        // Mock shopping cart id
        ShoppingCart shoppingCart = fakeShoppingCart(user);

        // Mock list cart item
        List<CartItem> cartItems = fakeListCartItem();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), user.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findAllByShoppingCartId(shoppingCart.getId())).thenReturn(cartItems);

        // Call api
        mockMvc.perform(get(CART_ITEM_URL)
            .header(HEADER_STRING, token)
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
        // Create admin token
        String token = generateAdminToken();

        // Call api
        mockMvc.perform(get(CART_ITEM_DETAIL_URL, generateLongNumber())
            .header(HEADER_STRING, token))
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
        String token = TokenAuthenticationService.createToken(user);

        // Mock cart item id and shopping cart id
        Long cartItemId = generateLongNumber();
        Long shoppingCartId = generateLongNumber();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCartId, user.getId())).thenReturn(null);

        // Call api
        mockMvc.perform(get(CART_ITEM_DETAIL_URL, cartItemId)
            .header(HEADER_STRING, token)
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
        String token = TokenAuthenticationService.createToken(user);

        // Mock shopping cart and cart item
        ShoppingCart shoppingCart = fakeShoppingCart(user);
        CartItem cartItem = fakeCartItem();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), user.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId()))
            .thenReturn(null);

        // Call api
        mockMvc.perform(get(CART_ITEM_DETAIL_URL, cartItem.getId())
            .header(HEADER_STRING, token)
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
        String token = TokenAuthenticationService.createToken(user);

        // Mock shopping cart and cart item
        ShoppingCart shoppingCart = fakeShoppingCart(user);
        CartItem cartItem = fakeCartItem();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCart.getId(), user.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId()))
            .thenReturn(cartItem);

        // Call api
        mockMvc.perform(get(CART_ITEM_DETAIL_URL, cartItem.getId())
            .header(HEADER_STRING, token)
            .param("shoppingCartId", shoppingCart.getId().toString()))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.product.id", is(cartItem.getProduct().getId())))
            .andExpect(jsonPath("$.shoppingCart.id", is(cartItem.getShoppingCart().getId())))
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
        // Create admin token
        String token = generateAdminToken();

        // Call api
        mockMvc.perform(put(CART_ITEM_DETAIL_URL, generateLongNumber())
            .header(HEADER_STRING, token))
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
        String token = TokenAuthenticationService.createToken(user);

        // Mock data
        CartItemUpdate cartItemUpdate = fakeCartItemUpdate();
        CartItem cartItem = fakeCartItem();

        // Mock method
        when(shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), user.getId())).thenReturn(null);

        // Call api
        mockMvc.perform(put(CART_ITEM_DETAIL_URL, cartItem.getId())
            .header(HEADER_STRING, token)
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
        String token = TokenAuthenticationService.createToken(user);

        // Mock data
        CartItemUpdate cartItemUpdate = fakeCartItemUpdate();
        CartItem cartItem = fakeCartItem();
        ShoppingCart shoppingCart = fakeShoppingCart(ShoppingCartStatus.DONE);

        // Mock method
        when(shoppingCartRepository.findOne(cartItemUpdate.getShoppingCartId(), user.getId())).thenReturn(shoppingCart);

        // Call api
        mockMvc.perform(put(CART_ITEM_DETAIL_URL, cartItem.getId())
            .header(HEADER_STRING, token)
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

        // Fake token
        String token = TokenAuthenticationService.createToken(user);

        // Mock data
        ShoppingCart shoppingCart = fakeShoppingCart(user);
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
            .header(HEADER_STRING, token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(cartItemUpdate)))
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
        String token = TokenAuthenticationService.createToken(user);

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
            .header(HEADER_STRING, token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(cartItemUpdate)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.product.id", is(cartItem.getProduct().getId())))
            .andExpect(jsonPath("$.shoppingCart.id", is(cartItem.getShoppingCart().getId())))
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
        // Create admin token
        String token = generateAdminToken();

        // Call api
        mockMvc.perform(delete(CART_ITEM_DETAIL_URL, generateLongNumber())
            .header(HEADER_STRING, token))
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
        String token = TokenAuthenticationService.createToken(user);

        // Mock data
        Long shoppingCartId = generateLongNumber();
        Long cartItemId = generateLongNumber();

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCartId, user.getId())).thenReturn(null);

        // Call api
        mockMvc.perform(delete(CART_ITEM_DETAIL_URL, cartItemId)
            .header(HEADER_STRING, token)
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
        String token = TokenAuthenticationService.createToken(user);

        // Mock data
        Long shoppingCartId = generateLongNumber();
        Long cartItemId = generateLongNumber();
        ShoppingCart shoppingCart = fakeShoppingCart(user);
        shoppingCart.setId(shoppingCartId);

        // Mock method
        when(shoppingCartRepository.findOne(shoppingCartId, user.getId())).thenReturn(shoppingCart);
        when(cartItemRepository.findOneByCartItemIdAndShoppingCartId(cartItemId, shoppingCartId))
            .thenReturn(null);

        // Call api
        mockMvc.perform(delete(CART_ITEM_DETAIL_URL, cartItemId)
            .header(HEADER_STRING, token)
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
        String token = TokenAuthenticationService.createToken(user);

        // Mock data
        Long shoppingCartId = generateLongNumber();
        Long cartItemId = generateLongNumber();
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
            .header(HEADER_STRING, token)
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
