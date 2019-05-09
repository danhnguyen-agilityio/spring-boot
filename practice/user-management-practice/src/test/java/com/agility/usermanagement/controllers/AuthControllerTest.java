package com.agility.usermanagement.controllers;

import com.agility.usermanagement.constants.RoleName;
import com.agility.usermanagement.dto.UserRequest;
import com.agility.usermanagement.models.Role;
import com.agility.usermanagement.models.User;
import com.agility.usermanagement.securities.AuthenticationRequest;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static com.agility.usermanagement.exceptions.CustomError.BAD_CREDENTIALS;
import static com.agility.usermanagement.exceptions.CustomError.USERNAME_ALREADY_EXISTS;
import static com.agility.usermanagement.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class AuthControllerTest extends BaseControllerTest {

    private User user;
    private AuthenticationRequest credentialRequest;
    private UserRequest userRequest;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();

        user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setActive(true);

        credentialRequest = new AuthenticationRequest();
        credentialRequest.setUsername("user");
        credentialRequest.setPassword("user");

        userRequest = new UserRequest();
        userRequest.setUsername("user");
        userRequest.setPassword("user");
    }

    /* ========================== Test sign up ======================= */

    /**
     * Test sign in with correct credential
     */
    @Test
    public void testSignInWithCorrectCredential() throws Exception {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(post("/v1/auths/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credentialRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(securityConfig.getHeaderString(),
                CoreMatchers.containsString(securityConfig.getTokenPrefix())));
    }

    /**
     * Test sign in not success when username not found
     */
    @Test
    public void testSignInShouldReturnFailWhenUserNameNotFound() throws Exception {
        // Set username not found in system
        credentialRequest.setUsername("admin");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        mockMvc.perform(post("/v1/auths/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credentialRequest)))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    /**
     * Test sign in should throw bad account credential exception when returned user null id
     */
    @Test
    public void testSignInShouldThrowBadAccountCredentialExceptionWhenUserNullId() throws Exception {
        user.setId(null);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(post("/v1/auths/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credentialRequest)))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.code", is(BAD_CREDENTIALS.code())))
            .andExpect(jsonPath("$.message", is(BAD_CREDENTIALS.message())));
    }

    /* ========================== Test sign up app ======================= */

    /**
     * Test sign up should return exception when username registry exists
     */
    @Test
    public void testSignUpShouldReturnExceptionWhenUsernameExists() throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/v1/auths/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userRequest)))
            .andDo(print())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.code", is(USERNAME_ALREADY_EXISTS.code())))
            .andExpect(jsonPath("$.message", is(USERNAME_ALREADY_EXISTS.message())));

        verify(userRepository, times(1)).findByUsername(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * Test sign up should success when request data valid
     */
    @Test
    public void testSignUpShouldSuccessWhenRequestDataValid() throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/v1/auths/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.username", is(user.getUsername())))
            .andExpect(jsonPath("$.active", is(true)));

        verify(userRepository, times(1)).findByUsername(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }


    /* ========================== Test access authenticated requests ======================= */

    /**
     * Test access authenticated request should return error unauthorized when token invalid
     */
    @Test
    public void testAccessAuthenticatedRequestShouldReturnErrorWhenTokenInvalid() throws Exception {
        // Set user have role user
        user.getRoles().add(RoleName.USER);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(get("/me")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityConfig.getHeaderString(), "Bearer david nguyen beck cam"))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }
}
