package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.dto.ShoppingCartRequest;
import com.agility.shopping.cart.mappers.ShoppingCartMapper;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.repositories.ShoppingCartRepository;
import com.agility.shopping.cart.repositories.UserRepository;
import com.agility.shopping.cart.services.TokenAuthenticationService;
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
import java.util.Set;

import static com.agility.shopping.cart.constants.SecurityConstants.HEADER_STRING;
import static com.agility.shopping.cart.exceptions.CustomError.USER_NOT_FOUND;
import static com.agility.shopping.cart.utils.ConvertUtil.convertObjectToJsonBytes;
import static com.agility.shopping.cart.utils.FakerUtil.fakeShoppingCart;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class test RESTful api for shopping cart
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShoppingCartControllerTest {
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


    /**
     * Test create shopping cart success
     */
    @Test
    public void testCreateShoppingCartSuccess() throws Exception {
        // Mock shopping cart
        ShoppingCartRequest request = new ShoppingCartRequest();
        request.setName("poor cart");
        ShoppingCart shoppingCart = shoppingCartMapper.toShoppingCart(request);

        // Generate token have role member
        String username = "user";
        Set<String> roles = Sets.newSet(RoleType.MEMBER.getName());
        String token = TokenAuthenticationService.createToken(username, roles);

        // Mock method
        when(shoppingCartRepository.existsByName(username)).thenReturn(false);
        when(userRepository.findByUsername(username)).thenReturn(new User());
        when(shoppingCartRepository.save(any(ShoppingCart.class)))
            .thenReturn(shoppingCart);

        mockMvc.perform(post("/shopping-carts")
            .header(HEADER_STRING, token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(shoppingCart.getName())));

        verify(shoppingCartRepository.existsByName(request.getName()),
            times(1));
        verify(userRepository.findByUsername(username),
            times(1));
        verify(shoppingCartRepository.save(any(ShoppingCart.class)),
            times(1));
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
        ShoppingCartRequest request = new ShoppingCartRequest();
        request.setName("poor cart");

        // Generate token have role member
        String username = "user";
        Set<String> roles = Sets.newSet(RoleType.MEMBER.getName());
        String token = TokenAuthenticationService.createToken(username, roles);

        // Mock method
        when(shoppingCartRepository.existsByName(request.getName()))
            .thenReturn(true);

        mockMvc.perform(post("/shopping-carts")
            .header(HEADER_STRING, token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isConflict());

        verify(shoppingCartRepository.existsByName(username),
            times(1));
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test create shopping cart fail user not found
     * when username does not exist
     */
    @Test
    public void testCreateShoppingCartFailUserNotFoundWhenUsernameNotExist()
        throws Exception {

        // Mock shopping cart
        ShoppingCartRequest request = new ShoppingCartRequest();
        request.setName("poor cart");
        ShoppingCart shoppingCart = shoppingCartMapper.toShoppingCart(request);

        // Generate token have role member
        String username = "user";
        Set<String> roles = Sets.newSet(RoleType.MEMBER.getName());
        String token = TokenAuthenticationService.createToken(username, roles);

        // Mock method
        when(shoppingCartRepository.existsByName(username)).thenReturn(false);
        when(userRepository.findByUsername(username)).thenReturn(null);

        mockMvc.perform(post("/shopping-carts")
            .header(HEADER_STRING, token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(USER_NOT_FOUND.code())));

        verify(shoppingCartRepository.existsByName(request.getName()),
            times(1));
        verify(userRepository.findByUsername(username),
            times(1));
        verifyNoMoreInteractions(shoppingCartRepository);
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * Test find all shopping cart success
     */
    @Test
    public void testFindAllShoppingCartSuccess() throws Exception {
        // Mock list shopping cart
        List<ShoppingCart> shoppingCarts = Arrays.asList(fakeShoppingCart(),
            fakeShoppingCart());

        // Generate token have role member
        String username = "user";
        Set<String> roles = Sets.newSet(RoleType.MEMBER.getName());
        String token = TokenAuthenticationService.createToken(username, roles);

        // Mock method
        when(shoppingCartRepository.findAllByUsername(username))
            .thenReturn(shoppingCarts);

        mockMvc.perform(get("/shopping-carts")
            .header(HEADER_STRING, token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name",
                is(shoppingCarts.get(0).getName())))
            .andExpect(jsonPath("$[1].name",
                is(shoppingCarts.get(0).getName())));

        verify(shoppingCartRepository.findAllByUsername(username),
            times(1));
        verifyNoMoreInteractions(shoppingCartRepository);
    }

    /**
     * Test find all shopping cart fail forbidden
     */
    @Test
    public void testFindAllShoppingCartFailForbidden() throws Exception {
        // Generate token have role member
        String username = "user";
        Set<String> roles = Sets.newSet(RoleType.MEMBER.getName());
        String token = TokenAuthenticationService.createToken(username, roles);

        mockMvc.perform(get("/shopping-carts")
            .header(HEADER_STRING, token))
            .andExpect(status().isForbidden());
    }


}
