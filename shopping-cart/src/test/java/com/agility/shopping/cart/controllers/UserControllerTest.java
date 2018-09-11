package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.models.AccountCredential;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.repositories.UserRepository;
import com.agility.shopping.cart.services.UserService;
import com.agility.shopping.cart.utils.FakerUtil;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
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

import java.util.HashSet;
import java.util.Set;

import static com.agility.shopping.cart.constants.SecurityConstants.HEADER_STRING;
import static com.agility.shopping.cart.constants.SecurityConstants.TOKEN_PREFIX;
import static com.agility.shopping.cart.utils.ConvertUtil.convertObjectToJsonBytes;
import static com.agility.shopping.cart.utils.FakerUtil.generateString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
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

    @MockBean
    private UserRepository userRepository;

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
        User user = FakerUtil.fakeMemberUser();
        AccountCredential credential = new AccountCredential();
        credential.setUsername(user.getUsername());
        credential.setPassword(user.getPassword());

        when(userService.loadUserByUsername(user.getUsername()))
            .thenReturn(new org.springframework.security.core.userdetails.User(
                user.getUsername(), passwordEncoder.encode(user.getPassword()), new HashSet<>()
            ));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credential)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(HEADER_STRING,
                CoreMatchers.containsString(TOKEN_PREFIX)));
    }

    /**
     * Test login with incorrect credential
     */
    @Test
    public void loginWithIncorrectCredential() throws Exception {
        User user = FakerUtil.fakeMemberUser();
        AccountCredential credential = new AccountCredential();
        credential.setUsername(user.getUsername());
        // Fake password
        credential.setPassword(generateString());

        when(userService.loadUserByUsername(credential.getUsername()))
            .thenReturn(new org.springframework.security.core.userdetails.User(
                user.getUsername(), passwordEncoder.encode(user.getPassword()), new HashSet<>()
            ));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

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

    /**
     * Test authenticated user can access to api
     */
    @Test
    public void shouldAllowAccessToAuthenticatedUsers() throws Exception {
        log.debug("Test authenticated user can access to api");
        User user = FakerUtil.fakeMemberUser();
        AccountCredential credential = new AccountCredential();
        credential.setUsername(user.getUsername());
        credential.setPassword(user.getPassword());

        when(userService.loadUserByUsername(user.getUsername()))
            .thenReturn(new org.springframework.security.core.userdetails.User(
                user.getUsername(), passwordEncoder.encode(user.getPassword()), new HashSet<>()
            ));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        String token = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credential)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(HEADER_STRING,
                CoreMatchers.containsString(TOKEN_PREFIX)))
            .andReturn().getResponse().getHeader(HEADER_STRING);

        mockMvc.perform(get("/test")
            .header(HEADER_STRING, token))
            .andExpect(status().isOk());
    }

    /**
     * Test admin can access to admin api but not access to member api
     */
    @Test
    public void shouldAllowAccessToAdminApiWithAdminUser() throws Exception {
        User user = FakerUtil.fakeAdminUser();
        AccountCredential credential = new AccountCredential();
        credential.setUsername(user.getUsername());
        credential.setPassword(user.getPassword());

        when(userService.loadUserByUsername(user.getUsername()))
            .thenReturn(new org.springframework.security.core.userdetails.User(
                user.getUsername(), passwordEncoder.encode(user.getPassword()), new HashSet<>()
            ));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        String token = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credential)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(HEADER_STRING,
                CoreMatchers.containsString(TOKEN_PREFIX)))
            .andReturn().getResponse().getHeader(HEADER_STRING);

        mockMvc.perform(get("/admin")
            .header(HEADER_STRING, token))
            .andExpect(status().isOk());

        mockMvc.perform(get("/user")
            .header(HEADER_STRING, token))
            .andExpect(status().isForbidden());
    }

    /**
     * Test member user can access to member api but not access to admin api
     */
    @Test
    public void shouldAllowAccessToMemberApiWithMemberUser() throws Exception {
        User user = FakerUtil.fakeMemberUser();
        AccountCredential credential = new AccountCredential();
        credential.setUsername(user.getUsername());
        credential.setPassword(user.getPassword());

        when(userService.loadUserByUsername(user.getUsername()))
            .thenReturn(new org.springframework.security.core.userdetails.User(
                user.getUsername(), passwordEncoder.encode(user.getPassword()), new HashSet<>()
            ));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        String token = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credential)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(HEADER_STRING,
                CoreMatchers.containsString(TOKEN_PREFIX)))
            .andReturn().getResponse().getHeader(HEADER_STRING);

        mockMvc.perform(get("/user")
            .header(HEADER_STRING, token))
            .andExpect(status().isOk());

        mockMvc.perform(get("/admin")
            .header(HEADER_STRING, token))
            .andExpect(status().isForbidden());
    }

    /**
     * Test user can access to api when token is expired
     */
    // TODO:: Create another profile (application-test.yml)
    @Test
    public void shouldNotAllowAccessWhenExpiredToken() {
    }


}
