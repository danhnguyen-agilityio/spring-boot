package com.agility.shopping.cart.exceptions;

import com.agility.shopping.cart.configs.SecurityConfig;
import com.agility.shopping.cart.models.TestModel;
import com.agility.shopping.cart.services.TokenAuthenticationService;
import com.agility.shopping.cart.utils.ConvertUtil;
import com.agility.shopping.cart.services.FakerService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CustomExceptionHandlerTest {

    private static final Faker faker = new Faker();
    private String token;
    private TestModel test;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private FakerService fakerService;

    @Autowired
    private SecurityConfig securityConfig;

    @Before
    public void setUp() {
        test = new TestModel(faker.name().name(), faker.name().name());
        token = tokenAuthenticationService.createToken(fakerService.fakeMemberUser());
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();
    }

    /**
     * Test request api should throw bad request when method argument mismatch
     */
    @Test
    public void shouldThrowBadRequest_WhenMethodArgumentMismatch() throws Exception {
        mockMvc.perform(post("/globalErrors/{id}", faker.name().name())
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isBadRequest())
            .andDo(print())
            .andExpect(jsonPath("$.errors[0]", containsString("should be of type")));
    }

    /**
     * Test request api should throw not found when no handler for http request
     */
    @Test
    public void shouldThrowNotFound_WhenNoHandlerForHttpRequest() throws Exception {
        mockMvc.perform(post("/notFound")
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isNotFound())
            .andDo(print());
            // FIXME:: Not throw exception in env test
//            .andExpect(jsonPath("$.errors[0]", containsString("No handler found")));
    }

    /**
     * Test request api should throw method not allowed when http request method not supported
     */
    @Test
    public void shouldThrowMethodNotAllowed_WhenHttpRequestMethodNotSupported() throws Exception {
        mockMvc.perform(put("/globalErrors/{id}", faker.number().randomNumber())
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isMethodNotAllowed())
            .andDo(print())
            .andExpect(jsonPath("$.errors[0]", containsString("Supported methods are")));
    }

    /**
     * Test request api should throw unsupported media type when send invalid http media type
     */
    @Test
    public void shouldThrowUnsupportedMediaType_WhenSendInvalidHttpMediaType() throws Exception {
        mockMvc.perform(post("/globalErrors/{id}", faker.number().randomNumber())
            .header(securityConfig.getHeaderString(), token)
            .content(ConvertUtil.convertObjectToJsonBytes(test)))
            .andExpect(status().isUnsupportedMediaType())
            .andDo(print())
            .andExpect(jsonPath("$.errors[0]", containsString("media type is not supported")));
    }
}
