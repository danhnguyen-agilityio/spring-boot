package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dtos.UserCreatedRequest;
import com.agility.usermanagement.models.AppUser;
import com.agility.usermanagement.models.Role;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import static com.agility.usermanagement.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class PublicControllerTest extends BaseController {

    private UserCreatedRequest userCreatedRequest;
    private AppUser appUser;

    @Before
    public void setUp() {
        userCreatedRequest = UserCreatedRequest.builder()
            .email(faker.internet().emailAddress())
            .password("Deptrai_07") // TODO: Write util class to generate password
            .firstName(faker.name().firstName())
            .lastName(faker.name().lastName())
            .build();
    }

    @Test
    public void testSignUpExistsUser() {

    }

    @Test
    public void testSignUpNewUser() throws Exception {
        // when
        mockMvc.perform(post("/api/v1/public/signup")
            .header("Authorization","Bearer " + TOKEN)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userCreatedRequest)))
            .andDo(print())
            // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(notNullValue())))
            .andExpect(jsonPath("$.email", is(userCreatedRequest.getEmail())))
            .andExpect(jsonPath("$.firstName", is(userCreatedRequest.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(userCreatedRequest.getLastName())))
            .andExpect(jsonPath("$.active", is(true)))
            .andExpect(jsonPath("$.roles[0]", is(Role.USER.toString())));
    }
}