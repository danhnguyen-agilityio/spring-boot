package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dtos.AuthResponse;
import com.agility.usermanagement.dtos.UserCreatedRequest;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public abstract class BaseControllerTest {

    // native app client, used for support get access token with resource owner password flow
    public final static String CLIENT_ID = "0oaqipsh5cectiRx0356";
    public final static String CLIENT_SECRET = "_r4oQjlVmSW7_dOkW5BkBHs1sjhNV9scF4_loz-1";

    protected static String userToken;
    protected static String managerToken;
    protected static String adminToken;


    protected Faker faker = new Faker();

    @Autowired
    protected MockMvc mockMvc;

    @BeforeClass
    public static void onceExecutedBeforeAll() throws Exception {
        userToken = getAccessToken("user@gmail.com", "Deptrai07");
        managerToken = getAccessToken("manager@gmail.com", "Deptrai07");
        adminToken = getAccessToken("admin@gmail.com", "Deptrai07");
    }

    @Before
    public void setUp() {
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
