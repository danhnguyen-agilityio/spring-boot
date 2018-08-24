package com.agility.spring.controllers;

import com.agility.spring.models.User;
import com.agility.spring.repositorys.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.nio.file.AccessDeniedException;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * This class used to test security
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SecurityTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRestController userRestController;

    /**
     * Test get user not logged in
     */
    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testGetUserNotLoggedIn() {
        userRestController.getUser(200);
    }

    /**
     * Test get user with role user
     */
    @Test(expected = AccessDeniedException.class)
    @WithMockUser
    public void testGetUserNotAdmin() {
        userRestController.getUser(200l);

    }

    /**
     * Test get user with role admin
     */
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testGetUserValidUser() {
        when(userRepository.findById(200l)).
            thenReturn(Optional.of(new User()));

        assertNotNull(userRestController.getUser(200l));
    }
}
