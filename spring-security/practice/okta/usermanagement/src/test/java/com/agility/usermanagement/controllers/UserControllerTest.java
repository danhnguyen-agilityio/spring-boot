package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dtos.AuthResponse;
import com.agility.usermanagement.dtos.UserCreatedRequest;
import com.agility.usermanagement.dtos.UserUpdatedRequest;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static com.agility.usermanagement.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.assertj.core.api.Assertions.assertThat;
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

    // native app client, used for support get access token with resource owner password flow
    public final static String CLIENT_ID = "0oaqipsh5cectiRx0356";
    public final static String CLIENT_SECRET = "_r4oQjlVmSW7_dOkW5BkBHs1sjhNV9scF4_loz-1";

    private static String userToken;
    private static String managerToken;
    private static String adminToken;

    private UserCreatedRequest userCreatedRequest;
    private UserUpdatedRequest userUpdatedRequest;

    @BeforeClass
    public static void onceExecutedBeforeAll() throws Exception {
        userToken = getAccessToken("user@gmail.com", "Deptrai07");
        managerToken = getAccessToken("manager@gmail.com", "Deptrai07");
        adminToken = getAccessToken("admin@gmail.com", "Deptrai07");
    }

    @Before
    public void setUp() throws Exception {
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
        testGetSelfInfo(userToken);
    }

    @Test
    public void testGetSelfInfoWithManagerToken() throws Exception {
        testGetSelfInfo(managerToken);
    }

    @Test
    public void testGetSelfInfoWithAdminToken() throws Exception {
        testGetSelfInfo(adminToken);
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
            .header("Authorization","Bearer " + userToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isForbidden());
    }

    @Test
    public void testGetAllUserWithManagerToken() throws Exception {
        mockMvc.perform(get("/api/v1/users")
            .header("Authorization","Bearer " + managerToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isOk());
    }

    @Test
    public void testGetAllUserWithAdminToken() throws Exception {
        mockMvc.perform(get("/api/v1/users")
            .header("Authorization","Bearer " + adminToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isOk());
    }

    //=============================== Get User By Id ================================

    @Test
    public void testGetUserWithUserToken() throws Exception {
        mockMvc.perform(get("/api/v1/users/" + faker.number().randomDigit())
            .header("Authorization","Bearer " + userToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isForbidden());
    }

    @Test
    public void testGetUserWithManagerToken() throws Exception {
        testGetUserSuccess(managerToken);
    }

    @Test
    public void testGetUserWithAdminToken() throws Exception {
        testGetUserSuccess(adminToken);
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
        testUpdateSelfInfoSuccess(userToken);
    }

    @Test
    public void testUpdateSelfInfoWithManagerToken() throws Exception {
        testUpdateSelfInfoSuccess(managerToken);
    }

    @Test
    public void testUpdateSelfInfoWithAdminToken() throws Exception {
        testUpdateSelfInfoSuccess(adminToken);
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
            .header("Authorization","Bearer " + userToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isForbidden());
    }

    @Test
    public void testDeactivateUserWithManagerToken() throws Exception {
        testDeactivateUserNotFound(managerToken);
        testDeactivateUserSuccess(managerToken);
    }

    @Test
    public void testDeactivateUserWithAdminToken() throws Exception {
        testDeactivateUserNotFound(adminToken);
        testDeactivateUserSuccess(adminToken);
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
            .header("Authorization","Bearer " + userToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // then
            .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteUserWithManagerToken() throws Exception {
        testDeleteUserNotFound(managerToken);
        testDeleteUserSuccess(managerToken);
    }

    @Test
    public void testDeleteUserWithAdminToken() throws Exception {
        testDeleteUserNotFound(adminToken);
        testDeleteUserSuccess(adminToken);
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

    public String sstring (){
        return "a";
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

    /**
     * Call api /token from authorization server to get access token (resource owner password flow)
     *
     * @param username name of user
     * @param password password of user
     *
     * @return Access token
     */
    private static String getAccessToken(String username, String password) throws Exception{
        TestRestTemplate testRestTemplate = new TestRestTemplate();

        String uri = UriComponentsBuilder.fromHttpUrl("https://dev-343362.okta.com/oauth2/default/v1/token")
            .queryParam("grant_type", "password")
            .queryParam("username", username)
            .queryParam("password", password)
            .queryParam("scope", "openid")
            .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.toString());
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<AuthResponse> responseEntity = testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            entity,
            AuthResponse.class
            );

        assertThat(responseEntity.getStatusCode())
            .as("Return status 200 when get access token")
            .isEqualTo(HttpStatus.OK);

        return responseEntity.getBody().getAccessToken();
    }
}