package com.agility.oauth2example2.resources;

import com.agility.oauth2example2.model.Welcome;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void noAuth() {
        // given
        String testName = "test";
        String request = "/api/hello?name=" + testName;

        // when
        ResponseEntity<Welcome> response = restTemplate.getForEntity(request, Welcome.class);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

}
