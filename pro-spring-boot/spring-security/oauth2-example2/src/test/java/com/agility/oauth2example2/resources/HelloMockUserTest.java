package com.agility.oauth2example2.resources;

import com.agility.oauth2example2.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@AutoConfigureMockMvc
public class HelloMockUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void shouldAllAnyAuthenticatedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/hello?name=Seb")
            .accept(MediaType.ALL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.greetings", is("Welcome Seb!")));
    }

    @Test
    @WithUserDetails("user@test.com")
    public void shouldAllowUserWithNoAuthorities() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/hello?name=Seb")
            .accept(MediaType.ALL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.greetings", is("Welcome Seb!")));
    }

    @Test
    public void shouldRejectIfNoAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/hello?name=Seb")
            .accept(MediaType.ALL))
            .andExpect(status().isUnauthorized());
    }
}
