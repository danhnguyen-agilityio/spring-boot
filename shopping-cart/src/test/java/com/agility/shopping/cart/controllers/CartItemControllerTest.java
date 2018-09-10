package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.dto.CartItemRequest;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.agility.shopping.cart.configs.WebSecurityConfig.CART_ITEM_URL;
import static com.agility.shopping.cart.constants.SecurityConstants.HEADER_STRING;
import static com.agility.shopping.cart.exceptions.CustomError.PRODUCT_NOT_FOUND;
import static com.agility.shopping.cart.exceptions.CustomError.SHOPPING_CART_NOT_FOUND;
import static com.agility.shopping.cart.utils.ConvertUtil.convertObjectToJsonBytes;
import static com.agility.shopping.cart.utils.FakerUtil.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CartItemRepository cartItemRepository;

    @MockBean
    private CartItemMapper cartItemMapper;

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
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(SHOPPING_CART_NOT_FOUND.code())));

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
        ShoppingCart shoppingCart = fakeShoppingCart(ShoppingCartStatus.INPROGRESS);

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
        ShoppingCart shoppingCart = fakeShoppingCart(ShoppingCartStatus.INPROGRESS);
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
        ShoppingCart shoppingCart = fakeShoppingCart(ShoppingCartStatus.INPROGRESS);
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


}
