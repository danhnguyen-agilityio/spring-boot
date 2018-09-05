package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.models.AccountCredential;
import com.agility.shopping.cart.services.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import java.util.HashSet;

import static com.agility.shopping.cart.constants.SecurityConstants.HEADER_STRING;
import static com.agility.shopping.cart.constants.SecurityConstants.TOKEN_PREFIX;
import static com.agility.shopping.cart.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();
    }

    /**
     * Test login with correct credential
     */
    @Test
    public void loginWithCorrectCredential() throws Exception {
        String username = "test";
        String password = "test";
        AccountCredential credential = new AccountCredential();
        credential.setUsername(username);
        credential.setPassword(password);

        when(userService.loadUserByUsername(credential.getUsername()))
            .thenReturn(new org.springframework.security.core.userdetails.User(
                username, passwordEncoder.encode(password), new HashSet<>()
            ));
        when(userService.getNameRolesByUsername(username))
            .thenReturn(new HashSet<>());

        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credential)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(HEADER_STRING,
                CoreMatchers.containsString(TOKEN_PREFIX));
    }

    /**
     * Test login with incorrect credential
     */
    @Test
    public void loginWithIncorrectCredential() throws Exception {
        String username = "test";
        String password = "test";
        AccountCredential credential = new AccountCredential();
        credential.setUsername("wrong");
        credential.setPassword("wrong");

        when(userService.loadUserByUsername(credential.getUsername()))
            .thenReturn(new org.springframework.security.core.userdetails.User(
                username, passwordEncoder.encode(password), new HashSet<>()
            ));
        when(userService.getNameRolesByUsername(username))
            .thenReturn(new HashSet<>());

        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credential)))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    /**
     * Test unauthenticated user can not access to api
     */
    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mockMvc.perform(get("/test"))
            .andExpect(status().isForbidden());
    }

}
