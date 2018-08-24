package com.agility.spring.controllers;

import com.agility.spring.dto.UserDTO;
import com.agility.spring.exceptions.CustomError;
import com.agility.spring.exceptions.NotFoundException;
import com.agility.spring.models.User;
import com.agility.spring.repositorys.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

/**
 * Test UserRestController
 */
@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerTest {

    @InjectMocks
    private UserRestController userRestController;

    @Mock
    private UserRepository userRepository;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * Test get user success
     */
    @Test
    public void testGetUserSuccess() {
        User mockUser = new User(200, "danh@gmail.com", "david");
        when(userRepository.findById(200l)).
            thenReturn(Optional.of(mockUser));
        UserDTO userDTO = userRestController.getUser(200l);

        assertNotNull(userDTO);
        assertEquals("david", userDTO.getLastName());
    }

    /**
     * Throw NotFoundException when user not exist
     */
    @Test
    public void throwNotFoundExceptionWhenUserNotExist() {
        when(userRepository.findById(200l))
            .thenThrow(new NotFoundException(CustomError.NOT_FOUND_USER));
        exception.expect(NotFoundException.class);
        exception.expectMessage("User is not found");
        userRestController.getUser(200l);

    }
}
