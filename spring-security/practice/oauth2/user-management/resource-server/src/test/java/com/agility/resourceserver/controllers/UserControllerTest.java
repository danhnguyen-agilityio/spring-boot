package com.agility.resourceserver.controllers;

import com.agility.resourceserver.dto.AuthResponse;
import com.agility.resourceserver.models.UserProfile;
import com.agility.resourceserver.repositorys.UserProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest {
    public final static String CLIENT_ID = "my-client";
    public final static String SECRET_ID = "my-secret";
    public final static String AUTHORIZATION_SERVER = "http://localhost:8080";

    private UserProfile userProfile;

    @MockBean
    private UserProfileRepository userProfileRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        userProfile = UserProfile.builder()
            .id(1L)
            .username("user")
            .firstName("firstName")
            .lastName("lastName")
            .address("address")
            .active(true)
            .build();

        when(userProfileRepository.findByUsername("user")).thenReturn(userProfile);
    }

    // =========================== Get self info user ===========================

    @Test
    @WithMockUser(username = "notfound")
    public void testGetSelfInfoForNotExistsUser() throws Exception {
        mockMvc.perform(get("/me"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user")
    public void testGetSelfInfoForExistsUser() throws Exception {
        mockMvc.perform(get("/me"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(notNullValue())))
            .andExpect(jsonPath("$.username", is(userProfile.getUsername())))
            .andExpect(jsonPath("$.active", is(userProfile.isActive())));
    }

    @Test
    public void getSelfInfoSuccess() throws Exception {
        String accessToken = obtainAccessToken("user", "password");
        mockMvc.perform(get("/me")
            .header("Authorization","Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isOk());
    }

    // =========================== Delete user ===========================

    // ======================== Log out ============================

    @Test
    public void logout() throws Exception {
        String accessToken = obtainAccessToken("user", "password");

        // Get data success
        mockMvc.perform(get("/me")
            .header("Authorization","Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isOk());

        // log out app
        mockMvc.perform(post("/logout-app")
            .header("Authorization","Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(true)));

        // get data fail un-authorize
        mockMvc.perform(get("/me")
            .header("Authorization","Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    /**
     * Get access token
     * @param username
     * @param password
     * @return access token
     * @throws Exception
     */
    private String obtainAccessToken(String username, String password) throws Exception {
        TestRestTemplate testRestTemplate = new TestRestTemplate();

        String uri = UriComponentsBuilder.fromHttpUrl(AUTHORIZATION_SERVER + "/oauth/token")
            .queryParam("grant_type", "password")
            .queryParam("username", username)
            .queryParam("password", password)
            .queryParam("scope", "read")
            .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setBasicAuth(CLIENT_ID, SECRET_ID);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<AuthResponse> responseEntity = testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            entity,
            AuthResponse.class);

        return responseEntity.getBody().getAccessToken();
    }

}