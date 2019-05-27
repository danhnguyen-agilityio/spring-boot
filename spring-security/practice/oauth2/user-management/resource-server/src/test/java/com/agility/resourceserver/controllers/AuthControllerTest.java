package com.agility.resourceserver.controllers;

import com.agility.resourceserver.dto.AuthRequest;
import com.agility.resourceserver.models.UserProfile;
import com.agility.resourceserver.repositorys.UserProfileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.agility.resourceserver.exceptions.CustomError.USERNAME_ALREADY_EXISTS;
import static com.agility.resourceserver.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class AuthControllerTest {

    private AuthRequest authRequest;
    private UserProfile userProfile;
    private static final Faker faker = new Faker();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileRepository userProfileRepository;

    @Before
    public void setUp() {
        userProfile = UserProfile.builder()
            .id(1L)
            .username("user")
            .build();

        authRequest = new AuthRequest("admin", "password");

        when(userProfileRepository.findByUsername("user")).thenReturn(userProfile);

    }

    // ============================= Login API =================================

    @Test
    public void testLoginSuccess() throws Exception {
        mockMvc.perform(post("/auths/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(authRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.access_token", is(notNullValue())));
    }

    // ============================= Register ===============================

    @Test
    public void testRegisterExistsUser() throws Exception {
        AuthRequest authRequest = new AuthRequest("user", "password");
        mockMvc.perform(post("/auths/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(authRequest)))
            .andDo(print())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.code", is(USERNAME_ALREADY_EXISTS.code())))
            .andExpect(jsonPath("$.message", is(USERNAME_ALREADY_EXISTS.message())));
    }

    @Test
    public void testRegisterNewUser() throws Exception {
        AuthRequest authRequest = new AuthRequest(faker.name().username(), "password");
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        mockMvc.perform(post("/auths/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(authRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(notNullValue())));
    }
}