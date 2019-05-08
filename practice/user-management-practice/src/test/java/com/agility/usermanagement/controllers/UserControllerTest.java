package com.agility.usermanagement.controllers;

import com.agility.usermanagement.constants.RoleName;
import com.agility.usermanagement.dto.UserRequest;
import com.agility.usermanagement.dto.UserUpdate;
import com.agility.usermanagement.models.Role;
import com.agility.usermanagement.models.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.agility.usermanagement.exceptions.CustomError.USERNAME_ALREADY_EXISTS;
import static com.agility.usermanagement.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class UserControllerTest extends BaseControllerTest {

    private User user;
    private List<User> users;
    private UserRequest userRequest;
    private String token;
    private UserUpdate userUpdate;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();

        user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setFirstName("firstName");
        user.setFirstName("lastName");
        user.setLastName("lastName");
        user.setAddress("address");
        user.setPassword(passwordEncoder.encode("user"));
        user.setActive(true);

        users = Arrays.asList(new User(1L, "david"), new User(2L, "tommy"), new User(3L, "beck"));

        userUpdate = new UserUpdate();
        userUpdate.setId(1L);
        userUpdate.setFirstName("firstNameUpdate");
        userUpdate.setLastName("lastNameUpdate");
        userUpdate.setAddress("addressUpdate");

        userRequest = new UserRequest();
        userRequest.setUsername("userRequest");
        userRequest.setPassword("userRequest");

        token = jwtTokenService.createToken(user);
    }

    // ============================= Test get self user info ============================

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
        // Set user have role admin
        user.getRoles().add(new Role(1L, RoleName.USER));
        user.getRoles().add(new Role(2L, RoleName.MANAGER));
        user.getRoles().add(new Role(3L, RoleName.ADMIN));

        testGetCurrentUserWithUserMock(user);
    }

    /**
     * Test get current user with user mock
     *
     * @param user User need mock
     * @throws Exception
     */
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


    // ============================= Test update self user info ============================

    /**
     * Test update self user info should success when user login
     */
    @Test
    public void testUpdateSelfUserInfoShouldSuccessWhenUserLogin() throws Exception {
        // Set user have role user
        user.getRoles().add(new Role(1L, RoleName.USER));

        testUpdateSelfInfoWithUserMock(user);
    }

    /**
     * Test update self user info should success when manager login
     */
    @Test
    public void testUpdateSelfUserInfoShouldSuccessWhenManagerLogin() throws Exception {
        // Set user have role manager
        user.getRoles().add(new Role(1L, RoleName.USER));
        user.getRoles().add(new Role(2L, RoleName.MANAGER));

        testUpdateSelfInfoWithUserMock(user);
    }

    /**
     * Test update self user info should success when user login
     */
    @Test
    public void testUpdateSelfUserInfoShouldSuccessWhenAdminLogin() throws Exception {
        // Set user have role admin
        user.getRoles().add(new Role(1L, RoleName.USER));
        user.getRoles().add(new Role(2L, RoleName.MANAGER));
        user.getRoles().add(new Role(3L, RoleName.ADMIN));

        testUpdateSelfInfoWithUserMock(user);
    }

    /**
     * Test update self user info with user mock
     *
     * @param user User need mock
     * @throws Exception
     */
    private void testUpdateSelfInfoWithUserMock(User user) throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(user)).thenReturn(user);

        mockMvc.perform(post("/me")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), token)
            .content(convertObjectToJsonBytes(userUpdate)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.firstName", is(userUpdate.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(userUpdate.getLastName())))
            .andExpect(jsonPath("$.address", is(userUpdate.getAddress())));

        verify(userRepository, times(2)).findByUsername(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    // ============================= Test Admin or Manager get user list ============================

    /**
     * Test get user list fail when user login
     */
    @Test
    public void testGetUserListFailForbiddenWhenUserLogin() throws Exception {
        // Set user have role manager
        user.getRoles().add(new Role(1L, RoleName.USER));

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), token))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    /**
     * Test get user list success when manager login
     */
    @Test
    public void testGetUserListSuccessWhenManagerLogin() throws Exception {
        // Set user have role manager
        user.getRoles().add(new Role(1L, RoleName.USER));
        user.getRoles().add(new Role(2L, RoleName.MANAGER));

        testGetUserListWithUserMock(user);
    }

    /**
     * Test get user list success when admin login
     */
    @Test
    public void testGetUserListSuccessWhenAdminLogin() throws Exception {
        // Set user have role manager
        user.getRoles().add(new Role(1L, RoleName.USER));
        user.getRoles().add(new Role(2L, RoleName.MANAGER));

        testGetUserListWithUserMock(user);
    }

    /**
     * Test get user list success with user mock
     *
     * @param user user need mock
     * @throws Exception
     */
    private void testGetUserListWithUserMock(User user) throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));
        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), token))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(users.size())));

        verify(userRepository, times(1)).findByUsername(anyString());
        verify(userRepository, times(1)).findAll();
    }


    /* ========================== Test create user ======================= */

    /**
     * Test create user fail forbidden when user login
     */
    @Test
    public void testCreateUserFailForbiddenWhenUserLogin() throws Exception {
        // Set user have role manager
        user.getRoles().add(new Role(1L, RoleName.USER));

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), token)
            .content(convertObjectToJsonBytes(userRequest)))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    /**
     * Test create user fail resource exist when username exists
     */
    @Test
    public void testCreateUserFailResourceExistWhenUsernameExists() throws Exception {
        // Set user have role manager
        user.getRoles().add(new Role(1L, RoleName.USER));
        user.getRoles().add(new Role(2L, RoleName.MANAGER));

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(userRepository.findByUsername(userRequest.getUsername())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), token)
            .content(convertObjectToJsonBytes(userRequest)))
            .andDo(print())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.code", is(USERNAME_ALREADY_EXISTS.code())))
            .andExpect(jsonPath("$.message", is(USERNAME_ALREADY_EXISTS.message())));

        verify(userRepository, times(2)).findByUsername(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * Test create user should success when request data valid and manager login
     */
    @Test
    public void testCreateUserShouldSuccessWhenRequestDataValidAndManagerLogin() throws Exception {
        // Set user have role manager
        user.getRoles().add(new Role(1L, RoleName.USER));
        user.getRoles().add(new Role(2L, RoleName.MANAGER));

        testCreateUserSuccessWithAuthenticationUser(user);
    }

    /**
     * Test create user should success when request data valid and admin login
     */
    @Test
    public void testCreateUserShouldSuccessWhenRequestDataValidAndAdminLogin() throws Exception {
        // Set user have role admin
        user.getRoles().add(new Role(1L, RoleName.USER));
        user.getRoles().add(new Role(2L, RoleName.MANAGER));
        user.getRoles().add(new Role(2L, RoleName.ADMIN));

        testCreateUserSuccessWithAuthenticationUser(user);
    }

    /**
     * Test create user success
     *
     * @throws Exception
     */
    private void testCreateUserSuccessWithAuthenticationUser(User user) throws Exception {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(userRepository.findByUsername(userRequest.getUsername())).thenReturn(Optional.ofNullable(null));
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), token)
            .content(convertObjectToJsonBytes(userRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.username", is(user.getUsername())))
            .andExpect(jsonPath("$.active", is(true)));

        verify(userRepository, times(2)).findByUsername(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

}