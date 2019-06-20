package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dtos.UserCreatedRequest;
import com.agility.usermanagement.dtos.UserUpdatedRequest;
import com.agility.usermanagement.models.Role;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import static com.agility.usermanagement.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PublicControllerTest extends BaseControllerTest {

    private UserCreatedRequest userCreatedRequest;

    @Before
    public void setUp() {
        super.setUp();

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