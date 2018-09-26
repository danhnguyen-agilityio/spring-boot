package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.configs.SecurityConfig;
import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.dto.CartItemRequest;
import com.agility.shopping.cart.dto.CartItemUpdate;
import com.agility.shopping.cart.dto.ProductRequest;
import com.agility.shopping.cart.dto.ShoppingCartRequest;
import com.agility.shopping.cart.exceptions.CustomError;
import com.agility.shopping.cart.mappers.ProductMapper;
import com.agility.shopping.cart.mappers.ShoppingCartMapper;
import com.agility.shopping.cart.models.*;
import com.agility.shopping.cart.repositories.CartItemRepository;
import com.agility.shopping.cart.repositories.ProductRepository;
import com.agility.shopping.cart.repositories.ShoppingCartRepository;
import com.agility.shopping.cart.repositories.UserRepository;
import com.agility.shopping.cart.securities.JwtTokenService;
import com.agility.shopping.cart.services.FakerService;
import com.agility.shopping.cart.services.ShoppingCartService;
import com.github.javafaker.Faker;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.agility.shopping.cart.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * BaseControllerTest class used to define common data and method mock
 */
@Slf4j
public class BaseControllerTest {

    protected static final Faker faker = new Faker();
    protected User memberUser;
    protected User adminUser;
    protected String memberToken;
    protected String adminToken;
    protected Product product;
    protected ProductRequest productRequest;
    protected List<Product> products;
    protected ShoppingCart shoppingCart;
    protected ShoppingCartRequest shoppingCartRequest;
    protected List<ShoppingCart> shoppingCarts;
    protected CartItemRequest cartItemRequest;
    protected CartItem cartItem;
    protected CartItemUpdate cartItemUpdate;
    protected List<CartItem> cartItems;
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected FilterChainProxy filterChainProxy;

    @Autowired
    protected SecurityConfig securityConfig;

    @Autowired
    protected ShoppingCartService shoppingCartService;

    @Autowired
    protected JwtTokenService jwtTokenService;

    @Autowired
    protected FakerService fakerService;

    @Autowired
    protected ProductMapper productMapper;

    @Autowired
    protected ShoppingCartMapper shoppingCartMapper;

    @MockBean
    protected ShoppingCartRepository shoppingCartRepository;

    @MockBean
    protected ProductRepository productRepository;

    @MockBean
    protected CartItemRepository cartItemRepository;

    @MockBean
    protected UserRepository userRepository;

    @Before
    public void setUp() {
        memberUser = fakerService.fakeUser(RoleType.MEMBER);
        adminUser = fakerService.fakeUser(RoleType.ADMIN);
        memberToken = jwtTokenService.createToken(memberUser);
        adminToken = jwtTokenService.createToken(adminUser);
        product = fakerService.fakeProduct();
        productRequest = fakerService.fakeProductRequest();
        products = fakerService.fakeListProduct(3);
        shoppingCart = fakerService.fakeShoppingCart();
        shoppingCartRequest = fakerService.fakeShoppingCartRequest();
        shoppingCarts = fakerService.fakeListShoppingCart(3);
        cartItem = fakerService.fakeCartItem();
        cartItemRequest = fakerService.fakeCartItemRequest();
        cartItems = fakerService.fakeListCartItem(3);
        cartItemUpdate = fakerService.fakeCartItemUpdate();

        // Return member user when mock method findByUsername
        when(userRepository.findByUsername(memberUser.getUsername())).thenReturn(Optional.ofNullable(memberUser));
        when(userRepository.findByUsername(adminUser.getUsername())).thenReturn(Optional.ofNullable(adminUser));

        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();
    }

    /**
     * Test response data when perform request
     *
     * @param requestInfo Request info
     */
    protected void testResponseData(RequestInfo requestInfo) throws Exception {
        MultiValueMap<String, String> multiValueMapParam = new LinkedMultiValueMap<>();
        if (requestInfo.getParams() != null) {
            for (val param : requestInfo.getParams().entrySet()) {
                multiValueMapParam.add(param.getKey(), param.getValue().toString());
            }
        }

        ResultActions resultActions = mockMvc.perform(requestInfo.getRequest().params(multiValueMapParam)
            .header(securityConfig.getHeaderString(), requestInfo.getToken())
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(requestInfo.getBody())))
            .andDo(print())
            .andExpect(status().is(requestInfo.getHttpStatus().value()));

        if (requestInfo.getJsonMap() != null) {
            for (val entry : requestInfo.getJsonMap().entrySet()) {
                if (entry.getValue() instanceof Matcher) {
                    resultActions.andExpect(jsonPath(entry.getKey(), (Matcher) entry.getValue()));

                } else {
                    resultActions.andExpect(jsonPath(entry.getKey(), is(entry.getValue())));
                }

            }
        }
    }

    /**
     * Create json map with custom error code
     *
     * @param customError Custom error contain error code
     * @return Json map contain error code
     */
    protected Map<String, Object> createJsonMapError(CustomError customError) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("$.code", customError.code());
        return jsonMap;
    }

}
