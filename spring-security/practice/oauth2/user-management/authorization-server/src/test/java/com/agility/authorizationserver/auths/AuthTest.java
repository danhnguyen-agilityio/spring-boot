package com.agility.authorizationserver.auths;

import com.agility.authorizationserver.configs.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@AutoConfigureMockMvc
public class AuthTest {

    @Autowired
    private MockMvc mockMvc;

    // ============================ Get access token ======================
    @Test
    public void testGetTokenWithWrongClient() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", "wrong");
        params.add("password", "password");
        params.add("scope", "read write");

        mockMvc.perform(post("/oauth/token")
            .params(params)
            .with(httpBasic("wrong-client", "my-secret"))
            .accept("application/json;charset=UTF-8"))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetTokenWithWrongCredential() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", "wrong");
        params.add("password", "password");
        params.add("scope", "read write");

        mockMvc.perform(post("/oauth/token")
            .params(params)
            .with(httpBasic("my-client", "my-secret"))
            .accept("application/json;charset=UTF-8"))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetTokenWithCorrectClientAndCredential() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", "admin");
        params.add("password", "password");
        params.add("scope", "read write");

        mockMvc.perform(post("/oauth/token")
            .params(params)
            .with(httpBasic("my-client", "my-secret"))
            .accept("application/json;charset=UTF-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.access_token", is(notNullValue())));
    }

    // ============================ Check access token ======================

    @Test
    public void checkInvalidToken() throws Exception {
        // TODO::
    }

    @Test
    public void checkValidToken() throws Exception {
        String accessToken = obtainAccessToken("admin", "password");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", accessToken);

        mockMvc.perform(post("/oauth/check_token")
            .params(params)
            .with(httpBasic("my-client", "my-secret"))
            .accept("application/json;charset=UTF-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.user_name", is("admin")))
            .andExpect(jsonPath("$.authorities", hasSize(3)));
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

    /**
     * Get refresh token
     * @param username
     * @param password
     * @return refresh token
     * @throws Exception
     */
    private String obtainRefreshToken(String username, String password) throws Exception {
        String resultString = getResponseToken(username, password);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("refresh_token").toString();
    }
}
