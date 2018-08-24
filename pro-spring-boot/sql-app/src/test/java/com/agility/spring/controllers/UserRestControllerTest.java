package com.agility.spring.controllers;

import com.agility.spring.dto.UserDTO;
import com.agility.spring.exceptions.NotFoundException;
import com.agility.spring.repositorys.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Test UserRestController
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRestControllerTest {

    @Autowired
    UserRestController userRestController;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * Test get user success
     */
    @Test
    public void testGetUserSuccess() {
        UserDTO userDTO = userRestController.getUser(200);

        assertNotNull(userDTO);
        assertEquals("Danh", userDTO.getLastName());
    }

    /**
     * Throw NotFoundException when user not exist
     */
    @Test
    public void throwNotFoundExceptionWhenUserNotExist() {
        exception.expect(NotFoundException.class);
        userRestController.getUser(500);
    }

}
