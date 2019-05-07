package com.agility.usermanagement.controllers;

import com.agility.usermanagement.constants.RoleName;
import com.agility.usermanagement.exceptions.CustomError;
import com.agility.usermanagement.models.Role;
import com.agility.usermanagement.models.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static com.agility.usermanagement.exceptions.CustomError.INVALID_TOKEN;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserControllerTest extends BaseControllerTest {

    private User user;
    private String token;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();

        user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));

        token = jwtTokenService.createToken(user);
    }

    /**
     * Test get current user should success when user login
     */
    @Test
    public void testGetCurrentUserShouldSuccessWhenUserLogin() throws Exception {
        // Set user have role user
        user.getRoles().add(new Role(1L, RoleName.USER));

        testGetCurrentUserWithUserMock(user);
    }

    /**
     * Test get current user should success when manager login
     */
    @Test
    public void testGetCurrentUserShouldSuccessWhenManagerLogin() throws Exception {
        // Set user have role manager
        user.getRoles().add(new Role(1L, RoleName.USER));
        user.getRoles().add(new Role(2L, RoleName.MANAGER));

        testGetCurrentUserWithUserMock(user);
    }

    /**
     * Test get current user should success when admin login
     */
    @Test
    public void testGetCurrentUserShouldSuccessWhenAdminLogin() throws Exception {
        // Set user have role manager
        user.getRoles().add(new Role(1L, RoleName.USER));
        user.getRoles().add(new Role(2L, RoleName.MANAGER));
        user.getRoles().add(new Role(3L, RoleName.ADMIN));

        testGetCurrentUserWithUserMock(user);
    }

    private void testGetCurrentUserWithUserMock(User user) throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(get("/me")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), token))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.username", is(user.getUsername())))
            .andExpect(jsonPath("$.roles", hasSize(user.getRoles().size())));
    }


}