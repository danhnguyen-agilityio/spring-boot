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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

/**
 * This class used to test rest api for user
 */
//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * Test get user only use Mockito
     */
    @Test
    public void testGetUserUseMock() {
        User mockUser = new User(200, "danh@gmail.com", "david");
        UserRepository repository = mock(UserRepository.class);
        when(repository.findById(200l)).thenReturn(Optional.of(mockUser));
        UserController controller = new UserController(repository);

        UserDTO userDTO = controller.getUser(200l);

        assertNotNull(userDTO);
        assertEquals("david", userDTO.getLastName());
    }

    /**
     * Test get user use Mockito with Spring
     */
    @Test
    public void testGetUserSuccess() {
        User mockUser = new User(200, "danh@gmail.com", "david");
        when(userRepository.findById(200l)).
            thenReturn(Optional.of(mockUser));
        UserDTO userDTO = userController.getUser(200l);

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
        userController.getUser(200l);
    }
}
