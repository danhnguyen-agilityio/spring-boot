package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dtos.UserCreatedRequest;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import static com.agility.usermanagement.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest extends BaseControllerTest {

    private UserCreatedRequest userCreatedRequest;

    @Before
    public void setUp() {
        userCreatedRequest = UserCreatedRequest.builder()
            .email(faker.internet().emailAddress())
            .password("Deptrai_07") // TODO: Write util class to generate password
            .firstName(faker.name().firstName())
            .lastName(faker.name().lastName())
            .build();
    }

    //=============================== Get Self Info ==============================

    @Test
    public void testGetSelfInfoWithUserToken() throws Exception {
        testGetSelfInfo(USER_TOKEN);
    }

    @Test
    public void testGetSelfInfoWithManagerToken() throws Exception {
        testGetSelfInfo(MANAGER_TOKEN);
    }

    @Test
    public void testGetSelfInfoWithAdminToken() throws Exception {
        testGetSelfInfo(ADMIN_TOKEN);
    }

    //=============================== Get All User ================================

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

    //=============================== Get User By Id ================================

    @Test
    public void testGetUserWithUserToken() throws Exception {
        mockMvc.perform(get("/api/v1/users/" + faker.number().randomDigit())
            .header("Authorization","Bearer " + USER_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isForbidden());
    }

    @Test
    public void testGetUserSuccess() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/public/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userCreatedRequest)))
            .andDo(print())
            // then
            .andExpect(status().isOk())
            .andReturn().getResponse();

        // Get user id that created
        String userId = JsonPath.parse(response.getContentAsString()).read("$.id");

        mockMvc.perform(get("/api/v1/users/" + userId)
            .header("Authorization","Bearer " + ADMIN_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(response.getContentAsString()));
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(post("/api/v1/public/signup")
            .header("Authorization","Bearer " + USER_TOKEN)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userCreatedRequest)));

        mockMvc.perform(post("/api/v1/public/signup")
            .header("Authorization","Bearer " + USER_TOKEN)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userCreatedRequest)));
    }

    private void testGetSelfInfo(String token) throws Exception {
        mockMvc.perform(get("/api/v1/me")
            .header("Authorization","Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isOk());
    }
}