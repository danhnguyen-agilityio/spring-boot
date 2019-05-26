package com.agility.resourceserver.controllers;

import com.agility.resourceserver.dto.AuthRequest;
import com.agility.resourceserver.dto.AuthResponse;
import com.agility.resourceserver.dto.UserProfileResponse;
import com.agility.resourceserver.dto.UserResponse;
import com.agility.resourceserver.exceptions.ApiError;
import com.agility.resourceserver.exceptions.ResourceNotFoundException;
import com.agility.resourceserver.models.UserProfile;
import com.agility.resourceserver.repositorys.UserProfileRepository;
import com.agility.resourceserver.services.UserProfileService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

import static com.agility.resourceserver.exceptions.CustomError.USER_NOT_FOUND;
import static com.agility.resourceserver.utils.ConvertUtil.convertObjectToJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    private static final Faker faker = new Faker();
    private ObjectMapper objectMapper = new ObjectMapper();
    private UserProfile userProfile;
    private UserProfileResponse userProfileResponse;

    private JacksonTester jsonUserProfile;

    @MockBean
    private UserProfileRepository userProfileRepository;

    @MockBean
    private UserProfileService userProfileService;

    @Autowired
    private MockMvc mockMvc;

    @Rule
    JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Before
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL));

        userProfile = UserProfile.builder()
            .id(1L)
            .username("user")
            .firstName("firstName")
            .lastName("lastName")
            .address("address")
            .active(true)
            .build();

        userProfileResponse = UserProfileResponse.builder()
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
        // given
        given(userProfileService.findByUsername("notfound")).willThrow(new ResourceNotFoundException(USER_NOT_FOUND));

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/me"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andReturn().getResponse();

        // then
        softly.assertThat(response.getStatus()).as("Check status is not found when username not exists")
            .isEqualTo(HttpStatus.NOT_FOUND.value());
        softly.assertThat(response.getContentAsString()).as("Check error message when username not exists")
            .isEqualTo(jsonUserProfile.write(new ApiError(USER_NOT_FOUND.code(), USER_NOT_FOUND.message())).getJson());
        softly.assertThat(response.getContentAsString()).as("Check error message when username not exists")
            .isEqualTo(convertObjectToJsonString(new ApiError(USER_NOT_FOUND.code(), USER_NOT_FOUND.message())));
        then(response.getContentAsString()).as("Check error message when username not exists")
            .isEqualTo(convertObjectToJsonString(new ApiError(USER_NOT_FOUND.code(), USER_NOT_FOUND.message())));
    }

    @Test
    @WithMockUser(username = "user")
    public void testGetSelfInfoForExistsUser() throws Exception {
        // given
        given(userProfileService.findByUsername("user")).willReturn(userProfileResponse);


        // when
        MockHttpServletResponse response = mockMvc.perform(get("/me"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(notNullValue())))
            .andExpect(jsonPath("$.username", is(userProfile.getUsername())))
            .andExpect(jsonPath("$.active", is(userProfile.isActive())))
            .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonUserProfile.write(
            UserProfileResponse.builder()
                .id(1L)
                .username("user")
                .firstName("firstName")
                .lastName("lastName")
                .address("address")
                .active(true)
                .build()
        ).getJson());
    }

    @Test
    public void getSelfInfoSuccess() throws Exception {
        String accessToken = obtainAccessToken("user", "password");
        mockMvc.perform(get("/me")
            .header("Authorization","Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isOk());
    }

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

    // ========================== Delete user =========================

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testDeleteUserWhenUserLogin() throws Exception {
        mockMvc.perform(delete("/users/100")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", roles = {"MANAGER"})
    public void testDeleteNotExistsUserWhenManagerLogin() throws Exception {

        when(userProfileRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        mockMvc.perform(delete("/users/100")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(USER_NOT_FOUND.code())));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testDeleteNotExistsUserWhenAdminLogin() throws Exception {

        when(userProfileRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        mockMvc.perform(delete("/users/100")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(USER_NOT_FOUND.code())));
    }


    @Test
    @WithMockUser(username = "user", roles = {"MANAGER", "ADMIN"})
    public void testDeleteExistsUser() throws Exception{

        // register user
        AuthRequest authRequest = new AuthRequest(faker.name().username(), "password");
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        String contentAsString = mockMvc.perform(post("/auths/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(authRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(notNullValue())))
            .andReturn().getResponse().getContentAsString();

        UserResponse userResponse = objectMapper.readValue(contentAsString, UserResponse.class);


        // delete user
        when(userProfileRepository.findById(anyLong())).thenReturn(Optional.ofNullable(userProfile));

        mockMvc.perform(delete("/users/" + userResponse.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

        // occur error when delete user again
        mockMvc.perform(delete("/users/" + userResponse.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
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