package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.configs.SecurityConfig;
import com.agility.shopping.cart.models.AccountCredential;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.repositories.UserRepository;
import com.agility.shopping.cart.services.UserService;
import com.agility.shopping.cart.utils.FakerUtil;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
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
import static com.agility.shopping.cart.utils.ConvertUtil.convertObjectToJsonBytes;
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

    private static final Faker faker = new Faker();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityConfig securityConfig;

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
     * TestModel login with correct credential
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
            .andExpect(header().string(securityConfig.getHeaderString(),
                CoreMatchers.containsString(securityConfig.getTokenPrefix())));
    }

    /**
     * TestModel login with incorrect credential
     */
    @Test
    public void loginWithIncorrectCredential() throws Exception {
        User user = FakerUtil.fakeMemberUser();
        AccountCredential credential = new AccountCredential();
        credential.setUsername(user.getUsername());
        // Fake password
        credential.setPassword(faker.internet().password());

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
     * TestModel unauthenticated user can not access to api
     */
    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mockMvc.perform(get("/test"))
            .andExpect(status().isForbidden());
    }

    /**
     * TestModel authenticated user can access to api
     */
    @Test
    public void shouldAllowAccessToAuthenticatedUsers() throws Exception {
        log.debug("TestModel authenticated user can access to api");
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
            .andExpect(header().string(securityConfig.getHeaderString(),
                CoreMatchers.containsString(securityConfig.getTokenPrefix())))
            .andReturn().getResponse().getHeader(securityConfig.getHeaderString());

        mockMvc.perform(get("/test")
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isOk());
    }

    /**
     * TestModel admin can access to admin api but not access to member api
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
            .andExpect(header().string(securityConfig.getHeaderString(),
                CoreMatchers.containsString(securityConfig.getTokenPrefix())))
            .andReturn().getResponse().getHeader(securityConfig.getHeaderString());

        mockMvc.perform(get("/admin")
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isOk());

        mockMvc.perform(get("/user")
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isForbidden());
    }

    /**
     * TestModel member user can access to member api but not access to admin api
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
            .andExpect(header().string(securityConfig.getHeaderString(),
                CoreMatchers.containsString(securityConfig.getTokenPrefix())))
            .andReturn().getResponse().getHeader(securityConfig.getHeaderString());

        mockMvc.perform(get("/user")
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isOk());

        mockMvc.perform(get("/admin")
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isForbidden());
    }

    /**
     * TestModel user can access to api when token is expired
     */
    // TODO:: Create another profile (application-test.yml)
    @Test
    public void shouldNotAllowAccessWhenExpiredToken() {
    }


}
