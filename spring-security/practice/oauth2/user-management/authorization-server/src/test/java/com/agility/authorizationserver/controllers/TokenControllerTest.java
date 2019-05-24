package com.agility.authorizationserver.controllers;

import com.agility.authorizationserver.models.Role;
import com.agility.authorizationserver.models.User;
import com.agility.authorizationserver.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TokenControllerTest {

    private static final String CLIENT_ID = "my-client";
    private static final String SECRET_ID = "my-secret";

    private User user;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        user = User.builder()
            .id(1L)
            .username("user")
            .password(passwordEncoder.encode("password"))
            .roles(Arrays.asList(Role.USER))
            .build();

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
    }

    // ========================= Revoke token ==============================

    @Test
    public void testRevokeAccessTokenWhenWrongAuthorizationBasic() throws Exception {
        mockMvc.perform(delete("/oauth/revoke")
            .param("token", "invalid token")
            .with(httpBasic("wrong-client", "wrong-secret"))
            .accept("application/json;charset=UTF-8"))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testRevokeInvalidAccessToken() throws Exception {
        mockMvc.perform(delete("/oauth/revoke")
            .param("token", "invalid token")
            .with(httpBasic(CLIENT_ID, SECRET_ID))
            .accept("application/json;charset=UTF-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(false)));
    }

    @Test
    public void testRevokeValidAccessToken() throws Exception {
        String accessToken = obtainAccessToken("user", "password");

        mockMvc.perform(delete("/oauth/revoke")
            .param("token", accessToken)
            .with(httpBasic(CLIENT_ID, SECRET_ID))
            .accept("application/json;charset=UTF-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(true)));
    }

    /**
     * Get response token
     * @param username
     * @param password
     * @return string response
     * @throws Exception
     */
    private String getResponseToken(String username, String password) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);
        params.add("scope", "read write");

        ResultActions result = mockMvc.perform(post("/oauth/token")
            .params(params)
            .with(httpBasic("my-client", "my-secret"))
            .accept("application/json;charset=UTF-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.access_token", is(notNullValue())));

        String resultString = result.andReturn().getResponse().getContentAsString();

        return resultString;
    }

    /**
     * Get access token
     * @param username
     * @param password
     * @return access token
     * @throws Exception
     */
    private String obtainAccessToken(String username, String password) throws Exception {
        String resultString = getResponseToken(username, password);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }
}