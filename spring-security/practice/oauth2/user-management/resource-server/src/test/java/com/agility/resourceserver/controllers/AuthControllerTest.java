package com.agility.resourceserver.controllers;

import com.agility.resourceserver.configs.SecurityProperties;
import com.agility.resourceserver.dto.AuthRequest;
import com.agility.resourceserver.utils.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.agility.resourceserver.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        authRequest = new AuthRequest("admin", "password");
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
}