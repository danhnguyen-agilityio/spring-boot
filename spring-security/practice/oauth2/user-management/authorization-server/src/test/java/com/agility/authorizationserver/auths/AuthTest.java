package com.agility.authorizationserver.auths;

import com.agility.authorizationserver.configs.TestConfig;
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
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User user = User.builder()
            .username("user")
            .password(passwordEncoder.encode("password"))
            .roles(Arrays.asList(Role.USER))
            .build();

        User manager = User.builder()
            .username("manager")
            .password(passwordEncoder.encode("password"))
            .roles(Arrays.asList(Role.USER, Role.MANAGER))
            .build();

        User admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("password"))
            .roles(Arrays.asList(Role.USER, Role.MANAGER, Role.ADMIN))
            .build();

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(userRepository.findByUsername("manager")).thenReturn(Optional.of(manager));
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
    }

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
    public void checkExpiredToken() throws Exception {
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

    // ============================ Get access token from refresh token ======================
    @Test
    public void testGetTokenFromValidRefreshToken() throws Exception {
        String refreshToken = obtainRefreshToken("admin", "password" );

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshToken);

        mockMvc.perform(post("/oauth/token")
            .params(params)
            .with(httpBasic("my-client", "my-secret"))
            .accept("application/json;charset=UTF-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.access_token", is(notNullValue())));
    }

    @Test
    public void testGetTokenFromExpiredRefreshToken() throws Exception {
       // TODO::
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
