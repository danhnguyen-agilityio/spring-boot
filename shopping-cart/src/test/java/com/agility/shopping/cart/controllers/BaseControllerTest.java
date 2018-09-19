package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.configs.SecurityConfig;
import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.dto.CartItemRequest;
import com.agility.shopping.cart.dto.CartItemUpdate;
import com.agility.shopping.cart.exceptions.CustomError;
import com.agility.shopping.cart.mappers.CartItemMapper;
import com.agility.shopping.cart.mappers.ProductMapper;
import com.agility.shopping.cart.mappers.ShoppingCartMapper;
import com.agility.shopping.cart.models.CartItem;
import com.agility.shopping.cart.models.Product;
import com.agility.shopping.cart.models.ShoppingCart;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.repositories.CartItemRepository;
import com.agility.shopping.cart.repositories.ProductRepository;
import com.agility.shopping.cart.repositories.ShoppingCartRepository;
import com.agility.shopping.cart.repositories.UserRepository;
import com.agility.shopping.cart.securities.JwtTokenService;
import com.agility.shopping.cart.services.FakerService;
import com.agility.shopping.cart.services.ShoppingCartService;
import com.agility.shopping.cart.services.UserService;
import com.github.javafaker.Faker;
import lombok.val;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
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
public class BaseControllerTest {

    protected static final Faker faker = new Faker();
    protected User memberUser;
    protected User adminUser;
    protected String memberToken;
    protected String adminToken;
    protected Product product;
    protected ShoppingCart shoppingCart;
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
    protected UserService userService;

    @Autowired
    protected ShoppingCartService shoppingCartService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

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
    protected void setUp() {
        memberUser = fakerService.fakeUser(RoleType.MEMBER);
        adminUser = fakerService.fakeUser(RoleType.ADMIN);
        adminUser.setUsername(memberUser.getUsername());
        memberToken = jwtTokenService.createToken(memberUser);
        adminToken = jwtTokenService.createToken(adminUser);
        product = fakerService.fakeProduct();
        shoppingCart = fakerService.fakeShoppingCart();
        cartItem = fakerService.fakeCartItem();
        cartItemRequest = fakerService.fakeCartItemRequest();
        cartItems = fakerService.fakeListCartItem(3);
        cartItemUpdate = fakerService.fakeCartItemUpdate();

        // Return member user when mock method findByUsername
        when(userRepository.findByUsername(memberUser.getUsername())).thenReturn(Optional.ofNullable(memberUser));

        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();
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
    protected void testResponseData(MockHttpServletRequestBuilder request,
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
    protected void testResponseData(MockHttpServletRequestBuilder request,
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
    protected Map<String, Object> createJsonMapError(CustomError customError) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("$.code", customError.code());
        return jsonMap;
    }
}
