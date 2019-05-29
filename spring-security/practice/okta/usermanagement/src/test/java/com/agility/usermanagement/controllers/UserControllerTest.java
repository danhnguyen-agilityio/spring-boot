package com.agility.usermanagement.controllers;

import com.agility.usermanagement.models.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest extends BaseControllerTest {

    @Before
    public void setUp() {

    }

    @Test
    public void testGetAllUserWithUserToken() throws Exception {
        mockMvc.perform(get("/api/v1/users")
            .header("Authorization","Bearer " + USER_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isForbidden());
    }

    @Test
    public void testGetAllUserWithManagerToken() throws Exception {
        mockMvc.perform(get("/api/v1/users")
            .header("Authorization","Bearer " + MANAGER_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isOk());
    }

    @Test
    public void testGetAllUserWithAdminToken() throws Exception {
        mockMvc.perform(get("/api/v1/users")
            .header("Authorization","Bearer " + ADMIN_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isOk());
    }
}