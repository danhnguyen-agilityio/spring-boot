package com.agility.resourceserver.controllers;

import com.agility.resourceserver.dto.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest {
    public final static String CLIENT_ID = "my-client";
    public final static String SECRET_ID = "my-secret";
    public final static String AUTHORIZATION_SERVER = "http://localhost:8080";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getSelfInfoSuccess() throws Exception {
        String accessToken = obtainAccessToken("admin", "password");
        mockMvc.perform(get("/me")
            .header("Authorization","Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isOk());
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