package com.agility.usermanagement.controllers;

import com.agility.usermanagement.models.Role;
import com.agility.usermanagement.dto.UserAuth;
import com.agility.usermanagement.dto.UserUpdate;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.agility.usermanagement.exceptions.CustomError.USERNAME_ALREADY_EXISTS;
import static com.agility.usermanagement.exceptions.CustomError.USER_NOT_FOUND;
import static com.agility.usermanagement.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private UserAuth userAuth;
    private String token;
    private UserUpdate userUpdate;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();

        user = User.builder()
            .id(1L)
            .username("user")
            .firstName("firstName")
            .lastName("lastName")
            .address("address")
            .password(passwordEncoder.encode("user"))
            .active(true)
            .roles(new ArrayList<>())
            .build();

        users = Arrays.asList(new User(1L, "david"), new User(2L, "tommy"), new User(3L, "beck"));

        userUpdate = UserUpdate.builder()
            .firstName("firstNameUpdate")
            .lastName("lastNameUpdate")
            .address("addressUpdate")
            .active(false)
            .build();

        userAuth = new UserAuth();
        userAuth.setUsername("userAuth");
        userAuth.setPassword("userAuth");

        token = jwtTokenService.createToken(user);
    }

    // ============================= Test get self user info ============================

    /**
     * Test get current user should success when user login
     */
    @Test
    public void testGetCurrentUserShouldSuccessWhenUserLogin() throws Exception {
        // Set user have role user
        user.getRoles().add(Role.USER);

        testGetCurrentUserWithUserMock(user);
    }

    /**
     * Test get current user should success when manager login
     */
    @Test
    public void testGetCurrentUserShouldSuccessWhenManagerLogin() throws Exception {
        // Set user have role manager
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);

        testGetCurrentUserWithUserMock(user);
    }

    /**
     * Test get current user should success when admin login
     */
    @Test
    public void testGetCurrentUserShouldSuccessWhenAdminLogin() throws Exception {
        // Set user have role admin
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);
        user.getRoles().add(Role.ADMIN);

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
        user.getRoles().add(Role.USER);

        testUpdateSelfInfoWithUserMock(user);
    }

    /**
     * Test update self user info should success when manager login
     */
    @Test
    public void testUpdateSelfUserInfoShouldSuccessWhenManagerLogin() throws Exception {
        // Set user have role manager
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);

        testUpdateSelfInfoWithUserMock(user);
    }

    /**
     * Test update self user info should success when user login
     */
    @Test
    public void testUpdateSelfUserInfoShouldSuccessWhenAdminLogin() throws Exception {
        // Set user have role admin
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);
        user.getRoles().add(Role.ADMIN);

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
        user.getRoles().add(Role.USER);

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
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);

        testGetUserListWithUserMock(user);
    }

    /**
     * Test get user list success when admin login
     */
    @Test
    public void testGetUserListSuccessWhenAdminLogin() throws Exception {
        // Set user have role manager
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);

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
        user.getRoles().add(Role.USER);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), token)
            .content(convertObjectToJsonBytes(userAuth)))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    /**
     * Test create user fail resource exist when username exists
     */
    @Test
    public void testCreateUserFailResourceExistWhenUsernameExists() throws Exception {
        // Set user have role manager
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(userRepository.findByUsername(userAuth.getUsername())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), token)
            .content(convertObjectToJsonBytes(userAuth)))
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
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);

        testCreateUserSuccessWithAuthenticationUser(user);
    }

    /**
     * Test create user should success when request data valid and admin login
     */
    @Test
    public void testCreateUserShouldSuccessWhenRequestDataValidAndAdminLogin() throws Exception {
        // Set user have role admin
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);
        user.getRoles().add(Role.ADMIN);

        testCreateUserSuccessWithAuthenticationUser(user);
    }

    /**
     * Test create user success
     *
     * @param user Authentication user
     * @throws Exception
     */
    private void testCreateUserSuccessWithAuthenticationUser(User user) throws Exception {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(userRepository.findByUsername(userAuth.getUsername())).thenReturn(Optional.ofNullable(null));
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), token)
            .content(convertObjectToJsonBytes(userAuth)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.username", is(user.getUsername())))
            .andExpect(jsonPath("$.active", is(true)));

        verify(userRepository, times(2)).findByUsername(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    /* ========================== Delete user ======================= */

    /**
     * Test delete user fail forbidden when user login
     */
    @Test
    public void testDeleteUserFailForbiddenWhenUserLogin() throws Exception {
        // Set user have role user
        user.getRoles().add(Role.USER);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(delete("/users/100")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), token))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    /**
     * Test delete user fail user not found when user id not found
     */
    @Test
    public void testDeleteUserShouldFailUserNotFoundWhenUserIdNotFound() throws Exception {
        // Set user have role manager
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        mockMvc.perform(delete("/users/100")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), token))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(USER_NOT_FOUND.code())));
    }

    /**
     * Test delete user success when manager login and user id exists
     */
    @Test
    public void testDeleteUserSuccessWhenManagerLogin() throws Exception {
        // Set user have role manager
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);

        testDeleteUserSuccessWithAuthenticationUser(user);
    }

    /**
     * Test delete user success when manager login and user id exists
     */
    @Test
    public void testDeleteUserSuccessWhenAdminLogin() throws Exception {
        // Set user have role manager
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);
        user.getRoles().add(Role.ADMIN);

        testDeleteUserSuccessWithAuthenticationUser(user);
    }

    /**
     * Test delete user success with authentication user
     *
     * @param user authentication user
     * @throws Exception
     */
    private void testDeleteUserSuccessWithAuthenticationUser(User user) throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(delete("/users/100")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), token))
            .andDo(print())
            .andExpect(status().isOk());
    }

    /* ========================== Update active user  ======================= */

    /**
     * Test update user fail forbidden when user login
     */
    @Test
    public void testUpdateUserForbiddenWhenUserLogin() throws Exception {
        // Set user have role user
        user.getRoles().add(Role.USER);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(put("/users/100/activate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userUpdate))
            .header(securityConfig.getHeaderString(), token))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    /**
     * Test update active user should fail when user id not found
     */
    @Test
    public void testUpdateActiveShouldFailWhenUserIdNotFound() throws Exception {
        // Set user have role manager
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(userRepository.findById(100L)).thenReturn(Optional.ofNullable(null));

        mockMvc.perform(put("/users/100/activate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userUpdate))
            .header(securityConfig.getHeaderString(), token))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    /**
     * Test update active user success when manager login and user id found
     */
    @Test
    public void testUpdateActiveSuccessWhenManagerLoginAndUserIdFound() throws Exception {
        // Set user have role manager
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);

        testUpdateActiveUserSuccessWithAuthenticationUser(user);
    }

    /**
     * Test update active user success when admin login and user id found
     */
    @Test
    public void testUpdateActiveSuccessWhenAdminLoginAndUserIdFound() throws Exception {
        // Set user have role admin
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);
        user.getRoles().add(Role.ADMIN);

        testUpdateActiveUserSuccessWithAuthenticationUser(user);
    }

    /**
     * Test update active user success with authentication user
     *
     * @param user authentication user
     * @throws Exception
     */
    private void testUpdateActiveUserSuccessWithAuthenticationUser(User user) throws Exception {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(userRepository.findById(100L)).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(put("/users/100/activate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userUpdate))
            .header(securityConfig.getHeaderString(), token))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.active", is(userUpdate.isActive())));
    }

    /* ========================== Change role user ======================= */

    /**
     * Test update role user fail forbidden when user login
     */
    @Test
    public void testUpdateRoleUserForbiddenWhenUserLogin() throws Exception {
        // Set user have role user
        user.getRoles().add(Role.USER);

        testUpdateRoleUserFailForbiddenWithAuthentication(user);
    }

    /**
     * Test update role user fail forbidden when manager login
     */
    @Test
    public void testUpdateRoleUserForbiddenWhenManagerLogin() throws Exception {
        // Set user have role admin
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);

        testUpdateRoleUserFailForbiddenWithAuthentication(user);
    }

    /**
     * Test update role user fail forbidden
     * @param user
     */
    private void testUpdateRoleUserFailForbiddenWithAuthentication(User user) throws Exception {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(put("/users/100/roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userUpdate))
            .header(securityConfig.getHeaderString(), token))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    /**
     * Test update role user should fail when user id not found
     */
    @Test
    public void testUpdateRoleUserShouldFailWhenUserIdNotFound() throws Exception {
        // Set user have role manager
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);
        user.getRoles().add(Role.ADMIN);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(userRepository.findById(100L)).thenReturn(Optional.ofNullable(null));

        mockMvc.perform(put("/users/100/roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userUpdate))
            .header(securityConfig.getHeaderString(), token))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    /**
     * Test update role user should fail format when role name wrong format
     */
    @Test
    public void testUpdateRoleUserShouldFailFormatWhenRoleNameWrongFormat() throws Exception {
        // Set user have role manager
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);
        user.getRoles().add(Role.ADMIN);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(userRepository.findById(100L)).thenReturn(Optional.ofNullable(user));

        // set role name invalid
        userUpdate.setRole("wrongFormat");

        mockMvc.perform(put("/users/100/roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userUpdate))
            .header(securityConfig.getHeaderString(), token))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    /**
     * Test update role user success when admin login
     */
    @Test
    public void testUpdateActiveSuccessWhenAdminLogin() throws Exception {
        testUpdateRoleUserSuccessWithAdminLoginAndRoleUpdate("USER");
        testUpdateRoleUserSuccessWithAdminLoginAndRoleUpdate("MANAGER");
        testUpdateRoleUserSuccessWithAdminLoginAndRoleUpdate("ADMIN");
    }

    /**
     * Test update role user success with admin user login and role update
     *
     * @param role new role update
     * @throws Exception
     */
    private void testUpdateRoleUserSuccessWithAdminLoginAndRoleUpdate(String role) throws Exception {
        // Set user have role admin
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.MANAGER);
        user.getRoles().add(Role.ADMIN);

        userUpdate.setRole(role);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(userRepository.findById(100L)).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(put("/users/100/roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userUpdate))
            .header(securityConfig.getHeaderString(), token))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.roles", hasSize(user.getRoles().size())));
    }
}

