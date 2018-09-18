package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.configs.SecurityConfig;
import com.agility.shopping.cart.constants.ShoppingCartStatus;
import com.agility.shopping.cart.dto.ShoppingCartRequest;
import com.agility.shopping.cart.mappers.ShoppingCartMapper;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.repositories.ShoppingCartRepository;
import com.agility.shopping.cart.repositories.UserRepository;
import com.agility.shopping.cart.securities.JwtTokenService;
import com.agility.shopping.cart.services.FakerService;
import com.agility.shopping.cart.services.ShoppingCartService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

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
public class ShoppingCartControllerTest {

    private static final Faker faker = new Faker();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private FakerService fakerService;

    @Autowired ShoppingCartService shoppingCartService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();
    }

    // ============================================================
    // Test create shopping cart
    // ============================================================

    /**
     * Test create shopping cart success
     */
    @Test
    public void testCreateShoppingCartSuccess() throws Exception {
        // Mock shopping cart
        ShoppingCart shoppingCart = fakerService.fakeShoppingCart();
        ShoppingCartRequest request = shoppingCartMapper.toShoppingCartRequest(shoppingCart);

        // Generate member token
        User user = fakerService.fakeMemberUser();
        String token = jwtTokenService.createToken(user);

        // Mock method
        when(shoppingCartRepository.existsByName(request.getName())).thenReturn(false);
        when(userRepository.findOne(user.getId())).thenReturn(user);
        when(shoppingCartRepository.save(any(ShoppingCart.class)))
            .thenReturn(shoppingCart);

        mockMvc.perform(post("/shopping-carts")
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(shoppingCart.getName())));

        verify(shoppingCartRepository, times(1)).existsByName(request.getName());
        verify(userRepository, times(1)).findOne(user.getId());
        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
        verifyNoMoreInteractions(shoppingCartRepository);
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * Test create shopping cart fail resource exists
     * when product name is already existed
     */
    @Test
    public void testCreateShoppingCartFailResourceExistsWhenProductNameExists()
        throws Exception {

        // Mock shopping cart
        ShoppingCartRequest request = fakerService.fakeShoppingCartRequest();

        // Generate member token
        User user = fakerService.fakeMemberUser();
        String token = jwtTokenService.createToken(user);

        // Mock method
        when(shoppingCartRepository.existsByName(request.getName()))
            .thenReturn(true);

        mockMvc.perform(post("/shopping-carts")
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isConflict());

        verify(shoppingCartRepository, times(1)).existsByName(request.getName());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test create shopping cart fail user not found
     * when username does not exist
     */
    @Test
    public void testCreateShoppingCartFailUserNotFoundWhenUsernameNotExist()
        throws Exception {

        // Mock shopping cart request
        ShoppingCartRequest request = fakerService.fakeShoppingCartRequest();

        // Generate member token
        User user = fakerService.fakeMemberUser();
        String token = jwtTokenService.createToken(user);

        // Mock method
        when(shoppingCartRepository.existsByName(request.getName())).thenReturn(false);
        when(userRepository.findOne(user.getId())).thenReturn(null);

        mockMvc.perform(post("/shopping-carts")
            .header(securityConfig.getHeaderString(), token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(USER_NOT_FOUND.code())));

        verify(shoppingCartRepository, times(1)).existsByName(request.getName());
        verify(userRepository, times(1)).findOne(user.getId());
        verifyNoMoreInteractions(shoppingCartRepository);
        verifyNoMoreInteractions(userRepository);
    }

    // ============================================================
    // Test find all shopping cart
    // ============================================================

    /**
     * Test find all shopping cart success
     */
    @Test
    public void testFindAllShoppingCartSuccess() throws Exception {
        // Mock list shopping cart
        List<ShoppingCart> shoppingCarts = Arrays.asList(fakerService.fakeShoppingCart(),
            fakerService.fakeShoppingCart());

        // Generate token have role member
        User user = fakerService.fakeMemberUser();
        String token = jwtTokenService.createToken(user);

        // Mock method
        when(shoppingCartRepository.findAllByUsername(user.getUsername()))
            .thenReturn(shoppingCarts);

        mockMvc.perform(get("/shopping-carts")
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name",
                is(shoppingCarts.get(0).getName())))
            .andExpect(jsonPath("$[1].name",
                is(shoppingCarts.get(1).getName())));

        verify(shoppingCartRepository, times(1)).findAllByUsername(user.getUsername());
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test find all shopping cart fail forbidden for admin user
     */
    @Test
    public void testFindAllShoppingCartFailForbiddenForAdminUser() throws Exception {
        // Generate token have role admin
        User user = fakerService.fakeAdminUser();
        String token = jwtTokenService.createToken(user);

        mockMvc.perform(get("/shopping-carts")
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isForbidden());
    }

    // ============================================================
    // Test find one shopping cart
    // ============================================================

    /**
     * Test find one shopping cart throw forbidden exception for admin user
     */
    @Test
    public void testFindOneShoppingCartThrowForbiddenExceptionForAdminUser() throws Exception {
        // Generate token have role admin
        User user = fakerService.fakeAdminUser();
        String token = jwtTokenService.createToken(user);

        // Call api
        mockMvc.perform(get(SHOPPING_CART_DETAIL_URL, faker.number().randomNumber())
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isForbidden());
    }

    /**
     * Test find one shopping cart throw resource not found exception when shopping cart not exist
     */
    @Test
    public void testFineOneShoppingCartThrowResourceNotFoundExceptionWhenShoppingCartNotExist() throws Exception {
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
