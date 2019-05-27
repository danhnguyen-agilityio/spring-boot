package com.agility.springJWT.controllers;

import com.agility.springJWT.configs.WebSecurityConfig;
import com.agility.springJWT.models.User;
import com.agility.springJWT.services.TokenAuthenticationService;
import com.agility.springJWT.services.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Mock
    private UserService userService;

    // Can use @MockBean to replace
    // private UserService userService;

    @Before
    public void setUp() {
//        Mockito.reset(userRepository);
        // Initializes fields annotated with Mockito annotations
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();
    }

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mockMvc.perform(get("/admin"))
            .andExpect(status().isForbidden());
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        String username = "admin";
        List<String> roles = Arrays.asList("ADMIN");
        String token = TokenAuthenticationService.createToken(username, roles);

        assertNotNull(token);
        mockMvc.perform(
            get("/admin").header("Authorization", token)
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldAllowAccessToAdminPageWithRoleAdmin() throws Exception {
        String username = "admin";
        String password = "password";
        User user = new User(username, password, null);
        List<String> roles = Arrays.asList("ADMIN");

        when(userService.loadUserByUsername(username)).thenReturn(new org.springframework.security.core.userdetails.User(
            username, password, Collections.emptyList()
        ));
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(userService.getRoles(username)).thenReturn(roles);

        String header = mockMvc.perform(
            post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(user)))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn().getResponse().getHeader("Authorization");
        assertNotNull(header);

        mockMvc.perform(
            get("/admin").header("Authorization", header))
            .andExpect(status().isOk());

        mockMvc.perform(
            get("/user")
            .header("Authorization", header))
            .andExpect(status().isForbidden());
    }

    @Test
    public void shouldAllowAccessToAdminPageAndUserPageWithUserHaveRoleAdminAndUser() throws Exception {

        String username = "admin";
        String password = "password";
        User user = new User(username, password, null);
        List<String> roles = Arrays.asList("ADMIN", "USER");

        when(userService.loadUserByUsername(username)).thenReturn(new org.springframework.security.core.userdetails.User(
            username, password, Collections.emptyList()
        ));
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(userService.getRoles(username)).thenReturn(roles);

        String header = mockMvc.perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(user)))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn().getResponse().getHeader("Authorization");
        assertNotNull(header);

        mockMvc.perform(
            get("/admin").header("Authorization", header))
            .andExpect(status().isOk());

        mockMvc.perform(
            get("/user")
                .header("Authorization", header))
            .andExpect(status().isOk());
    }

    /**
     * Write an object into JSON representation
     */
    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Convert object to bytes that contains the JSON representation of the object
     */
    public static byte[] convertObjectToJsonBytes(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Configure the created object to include only non null properties of the serialized object
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Convert the object as json string and return the created string as byte array
        return mapper.writeValueAsBytes(object);
    }
}