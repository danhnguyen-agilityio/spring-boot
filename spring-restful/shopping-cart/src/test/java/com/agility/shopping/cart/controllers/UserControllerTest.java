package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.models.RequestInfo;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.securities.AuthenticationRequest;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import static com.agility.shopping.cart.configs.WebSecurityConfig.CART_ITEM_URL;
import static com.agility.shopping.cart.utils.ConvertUtil.convertObjectToJsonBytes;
import static java.util.stream.Collectors.toList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserControllerTest extends BaseControllerTest {

    /**
     * Test login with correct credential
     */
    @Test
    public void loginWithCorrectCredential() throws Exception {
        User user = fakerService.fakeMemberUser();
        AuthenticationRequest credential = new AuthenticationRequest();
        credential.setUsername(user.getUsername());
        credential.setPassword(user.getPassword());

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credential)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(securityConfig.getHeaderString(),
                CoreMatchers.containsString(securityConfig.getTokenPrefix())));
    }

    /**
     * Test login with incorrect credential
     */
    @Test
    public void loginWithIncorrectCredential() throws Exception {
        User user = fakerService.fakeMemberUser();
        AuthenticationRequest credential = new AuthenticationRequest();
        credential.setUsername(user.getUsername());
        // Fake password
        credential.setPassword(faker.internet().password());

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credential)))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    /**
     * Test unauthenticated user can not access to api
     */
    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mockMvc.perform(get("/test"))
            .andExpect(status().isForbidden());
    }

    /**
     * Test authenticated user can access to api
     */
    @Test
    public void shouldAllowAccessToAuthenticatedUsers() throws Exception {
        log.debug("Test authenticated user can access to api");
        User user = fakerService.fakeMemberUser();
        AuthenticationRequest credential = new AuthenticationRequest();
        credential.setUsername(user.getUsername());
        credential.setPassword(user.getPassword());

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        String token = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credential)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(securityConfig.getHeaderString(),
                CoreMatchers.containsString(securityConfig.getTokenPrefix())))
            .andReturn().getResponse().getHeader(securityConfig.getHeaderString());

        mockMvc.perform(get("/test")
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isOk());
    }

    /**
     * Test admin can access to admin api but not access to member api
     */
    @Test
    public void shouldAllowAccessToAdminApiWithAdminUser() throws Exception {
        User user = fakerService.fakeAdminUser();
        AuthenticationRequest credential = new AuthenticationRequest();
        credential.setUsername(user.getUsername());
        credential.setPassword(user.getPassword());

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        String token = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credential)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(securityConfig.getHeaderString(),
                CoreMatchers.containsString(securityConfig.getTokenPrefix())))
            .andReturn().getResponse().getHeader(securityConfig.getHeaderString());

        mockMvc.perform(get("/admin")
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isOk());

        mockMvc.perform(get("/user")
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isForbidden());
    }

    /**
     * Test member user can access to member api but not access to admin api
     */
    @Test
    public void shouldAllowAccessToMemberApiWithMemberUser() throws Exception {
        User user = fakerService.fakeMemberUser();
        AuthenticationRequest credential = new AuthenticationRequest();
        credential.setUsername(user.getUsername());
        credential.setPassword(user.getPassword());

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        String token = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credential)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(securityConfig.getHeaderString(),
                CoreMatchers.containsString(securityConfig.getTokenPrefix())))
            .andReturn().getResponse().getHeader(securityConfig.getHeaderString());

        mockMvc.perform(get("/user")
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isOk());

        mockMvc.perform(get("/admin")
            .header(securityConfig.getHeaderString(), token))
            .andExpect(status().isForbidden());
    }

    /**
     * Test get current user should success when token valid
     */
    @Test
    public void testGetCurrentUserShouldSuccessWhenTokenIsValid() throws Exception {
        // Mock method
        when(userRepository.findByUsername(memberUser.getUsername())).thenReturn(Optional.ofNullable(memberUser));

        // Create jsonMap to map response data
        Map<Object, Object> jsonMap = new HashMap<>();
        jsonMap.put("$.username", memberUser.getUsername());
        jsonMap.put("$.roles[0]", memberUser.getRoles().stream().collect(toList()).get(0).getName());

        // Test response data for request
        testResponseData(RequestInfo.builder()
            .request(get("/me"))
            .token(memberToken)
            .httpStatus(HttpStatus.OK)
            .build());
    }
}
