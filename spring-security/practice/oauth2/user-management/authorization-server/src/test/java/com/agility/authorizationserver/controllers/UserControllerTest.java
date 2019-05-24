package com.agility.authorizationserver.controllers;

import com.agility.authorizationserver.models.Role;
import com.agility.authorizationserver.models.User;
import com.agility.authorizationserver.repository.UserRepository;
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

import java.util.Arrays;
import java.util.Optional;

import static com.agility.authorizationserver.exceptions.CustomError.USER_NOT_FOUND;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final String CLIENT_ID = "my-client";
    private static final String SECRET_ID = "my-secret";

    private User user;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        user = User.builder()
            .id(1L)
            .username("user")
            .roles(Arrays.asList(Role.USER))
            .build();

        when(userRepository.findByUsername("user")).thenReturn(Optional.ofNullable(user));
    }

    @Test
    public void testDeleteUserWithIncorrectBasicAuthentication() throws Exception{
        mockMvc.perform(delete("/users/100")
            .contentType(MediaType.APPLICATION_JSON)
            .with(httpBasic("incorrect-client", "incorrect-secret")))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteNotExistsUser() throws Exception{
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        mockMvc.perform(delete("/users/100")
            .contentType(MediaType.APPLICATION_JSON)
            .with(httpBasic(CLIENT_ID, SECRET_ID)))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(USER_NOT_FOUND.code())));
    }

    @Test
    public void testDeleteExistsUser() throws Exception{
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(delete("/users/100")
            .contentType(MediaType.APPLICATION_JSON)
            .with(httpBasic(CLIENT_ID, SECRET_ID)))
            .andDo(print())
            .andExpect(status().isOk());
    }

}