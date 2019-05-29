package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dtos.UserCreatedRequest;
import com.agility.usermanagement.models.AppUser;
import com.agility.usermanagement.models.Role;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static com.agility.usermanagement.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class PublicControllerTest {

    private static final String TOKEN = "eyJraWQiOiJJVHUyZVZTaWVrR2N2d19FRkYwYmgwMXlZSmV1Uy1ZMzFrd0NDV09Yc1k4IiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULjlyVXpreXU2NUVNWVA1aGI0ZlU3MDFuNXR6SVVqLVNid3FhWFQ5X3o2OTgiLCJpc3MiOiJodHRwczovL2Rldi0zNDMzNjIub2t0YS5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNTU5MDk3MzkyLCJleHAiOjE1NTkxODM3OTIsImNpZCI6IjBvYW40bXQ4eURzY0J3QU9GMzU2IiwidWlkIjoiMDB1bjRqenlwNFJ5a2JiRWszNTYiLCJzY3AiOlsib3BlbmlkIiwicHJvZmlsZSJdLCJzdWIiOiJkYW5obmd1eWVudGtAZ21haWwuY29tIiwiZ3JvdXBzIjpbIkV2ZXJ5b25lIl19.pfc3XfK4mnn3g9yKqC6MZiaAbxBZh5PTCbybOFTJ5GH7yZwBwdc2NWH9XsHQzNDKIY1-mN89QsPi-ph8CJDAWNA03q866t8709HtXRT2mcOjroO8fECxM9uI6TmBVbB_huETE7jt3ZDZkGIzPc2WE0TwMTfllCXl5dIYyYX-R03AfGdc0TKfxQkzP20cND6TTL817q3R49-T1mPHBFyKutrUF2PAjeAoJgFH8KxnRq3-TcDF1OPNtz8vMylLO8-RvYv63RkyoRJAulWiTtmQD_3JZzyltEw86-TTQ55rXILc7GBvIMJI-roO9e53aK65olH6v5OuLn9jtjZGAb8vOQ";

    private UserCreatedRequest userCreatedRequest;
    private AppUser appUser;

    private Faker faker = new Faker();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        userCreatedRequest = UserCreatedRequest.builder()
            .email(faker.internet().emailAddress())
            .password("Deptrai_07") // TODO: Write util class to generate password
            .firstName(faker.name().firstName())
            .lastName(faker.name().lastName())
            .build();
    }

    @Test
    public void testSignUpExistsUser() {

    }

    @Test
    public void testSignUpNewUser() throws Exception {
        // when
        mockMvc.perform(post("/api/v1/public/signup")
            .header("Authorization","Bearer " + TOKEN)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(userCreatedRequest)))
            .andDo(print())
            // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(notNullValue())))
            .andExpect(jsonPath("$.email", is(userCreatedRequest.getEmail())))
            .andExpect(jsonPath("$.firstName", is(userCreatedRequest.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(userCreatedRequest.getLastName())))
            .andExpect(jsonPath("$.roles[0]", is(Role.USER.toString())));
    }
}