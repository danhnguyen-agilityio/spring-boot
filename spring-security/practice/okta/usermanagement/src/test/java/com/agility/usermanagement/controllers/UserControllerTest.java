package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dtos.UserCreatedRequest;
import com.agility.usermanagement.dtos.UserUpdatedRequest;
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
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest extends BaseControllerTest {

    private UserCreatedRequest userCreatedRequest;
    private UserUpdatedRequest userUpdatedRequest;

    @Before
    public void setUp() {
        userCreatedRequest = UserCreatedRequest.builder()
            .email(faker.internet().emailAddress())
            .password("Deptrai_07") // TODO: Write util class to generate password
            .firstName(faker.name().firstName())
            .lastName(faker.name().lastName())
            .build();

        userUpdatedRequest = UserUpdatedRequest.builder()
            .email(faker.internet().emailAddress())
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

    private void testGetSelfInfo(String token) throws Exception {
        mockMvc.perform(get("/api/v1/me")
            .header("Authorization","Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isOk());
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
    public void testGetUserWithManagerToken() throws Exception {
        testGetUserSuccess(MANAGER_TOKEN);
    }

    @Test
    public void testGetUserWithAdminToken() throws Exception {
        testGetUserSuccess(ADMIN_TOKEN);
    }

    private void testGetUserSuccess(String token) throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/public/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userCreatedRequest)))
            .andDo(print())
            // then
            .andExpect(status().isOk())
            .andReturn().getResponse();

        log.debug("App user response: {}", response.getContentAsString());
        // Get user id that created
        String userId = JsonPath.read(response.getContentAsString(), "$.id");

        mockMvc.perform(get("/api/v1/users/" + userId)
            .header("Authorization","Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(response.getContentAsString()));
    }

    //=============================== Update self user info ================================

    @Test
    public void testUpdateSelfInfoWithUserToken() throws Exception {
        testUpdateSelfInfoSuccess(USER_TOKEN);
    }

    @Test
    public void testUpdateSelfInfoWithManagerToken() throws Exception {
        testUpdateSelfInfoSuccess(MANAGER_TOKEN);
    }

    @Test
    public void testUpdateSelfInfoWithAdminToken() throws Exception {
        testUpdateSelfInfoSuccess(ADMIN_TOKEN);
    }

    private void testUpdateSelfInfoSuccess(String token) throws Exception {
        mockMvc.perform(post("/api/v1/me")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization","Bearer " + token)
            .content(convertObjectToJsonBytes(userUpdatedRequest)))
            .andDo(print())
            // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is(userUpdatedRequest.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(userUpdatedRequest.getLastName())));
    }

    //=============================== Deactivate user ================================

    @Test
    public void testDeactivateUserWithUserToken() throws Exception {
        mockMvc.perform(post("/api/v1/users/" + faker.number().randomDigit() + "/deactivate")
            .header("Authorization","Bearer " + USER_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isForbidden());
    }

    @Test
    public void testDeactivateUserWithManagerToken() throws Exception {
        testDeactivateUserNotFound(MANAGER_TOKEN);
        testDeactivateUserSuccess(MANAGER_TOKEN);
    }

    @Test
    public void testDeactivateUserWithAdminToken() throws Exception {
        testDeactivateUserNotFound(ADMIN_TOKEN);
        testDeactivateUserSuccess(ADMIN_TOKEN);
    }

    private void testDeactivateUserNotFound(String token) throws Exception {
        mockMvc.perform(post("/api/v1/users/" + faker.number().randomDigit() + "/deactivate")
            .header("Authorization","Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isNotFound());
    }

    private void testDeactivateUserSuccess(String token) throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/public/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userCreatedRequest)))
            .andDo(print())
            // then
            .andExpect(status().isOk())
            .andReturn().getResponse();

        log.debug("App user response: {}", response.getContentAsString());
        // Get user id that created
        String userId = JsonPath.read(response.getContentAsString(), "$.id");

        mockMvc.perform(post("/api/v1/users/" + userId + "/deactivate")
            .header("Authorization","Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isOk());
    }

    //=============================== Delete user ================================

    @Test
    public void testDeleteUserWithUserToken() throws Exception {
        mockMvc.perform(delete("/api/v1/users/" + faker.number().randomDigit())
            .header("Authorization","Bearer " + USER_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteUserWithManagerToken() throws Exception {
        testDeleteUserNotFound(MANAGER_TOKEN);
        testDeleteUserSuccess(MANAGER_TOKEN);
    }

    @Test
    public void testDeleteUserWithAdminToken() throws Exception {
        testDeleteUserNotFound(ADMIN_TOKEN);
        testDeleteUserSuccess(ADMIN_TOKEN);
    }

    private void testDeleteUserNotFound(String token) throws Exception {
        mockMvc.perform(delete("/api/v1/users/" + faker.number().randomDigit())
            .header("Authorization","Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isNotFound());
    }

    private void testDeleteUserSuccess(String token) throws Exception {
        String userId = JsonPath.read(getCreatedUserJson(), "$.id");

        mockMvc.perform(delete("/api/v1/users/" + userId)
            .header("Authorization","Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isOk());
    }

    /**
     * Create new user and return json info of response
     */
    private String getCreatedUserJson() throws Exception {
        String createdUserJson = mockMvc.perform(post("/api/v1/public/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userCreatedRequest)))
            .andDo(print())
            // then
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();

        return createdUserJson;
    }
}