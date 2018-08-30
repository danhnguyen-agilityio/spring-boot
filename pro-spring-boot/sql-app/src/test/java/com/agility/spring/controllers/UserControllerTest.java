// NOTE Dependency
    // Hamcrest (hamcrest-all): Write assertions for the response
    //Mockito: Mocking library
    // JsonPath (Json-path and json-path-assert): Write assertions for JSON documents return by our REST API
    // Junit
    // Spring Test
// Targets
    // Learned to write unit tests for controller methods which read information from the database
    // Learned  to write unit tests for controller method which add information to the database
    // Learned how we can transform DTO objects in to JSON bytes and send the result of the transformation in the body of the request
    // Learned how we can write assertions for JSON documents by using JsonPath expressions

package com.agility.spring.controllers;

import com.agility.spring.dto.UserDTO;
import com.agility.spring.exceptions.CustomError;
import com.agility.spring.exceptions.HandleCustomException;
import com.agility.spring.exceptions.NotFoundException;
import com.agility.spring.models.User;
import com.agility.spring.repositorys.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class used to test rest api for user
 */
//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    @Autowired
    private UserController userController;

    // Can use MockBean to replace Mock + InjectMock
    @Mock
    private UserRepository userRepository;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        Mockito.reset(userRepository);
        // Initializes fields annotated with Mockito annotations
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build();
    }

    /**
     * Test get all user
     */
    @Test
    public void tetGetAllUserSuccess() throws Exception {
        List<User> users = Arrays.asList(
            new User(200, "danh@gmail.com", "david"),
            new User(300, "tu@gmail.com", "tu")
        );
        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(200)))
         .andExpect(jsonPath("$[0].customEmail").value("danh@gmail.com"))
         .andExpect(jsonPath("$[0].lastName", is("david")));

        // Verify that the findAll() method of the UserRepository is invoked exactly once
        verify(userRepository, times(1)).findAll();
        // Verify that after response, no more interactions are made to the UserService
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * Test create user fail
     */
    @Test
    public void testCreateUserFailBadRequest() throws Exception {
        User user = new User(200, "danh@gmail.com", "David");
        UserDTO userDTO = new UserDTO(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        mockMvc.perform(
            post("/users").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(userDTO)))
            .andExpect(status().isBadRequest());

        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * Test get user by id success
     */
    @Test
    public void testGetByIdSuccess() throws Exception {
        User user = new User(200, "danh@gmail.com", "David");
        when(userRepository.findById(200l)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/{id}", 200))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(("$.email"), is("danh@gmail.com")))
            .andExpect(jsonPath("$.lastName", is("David")));

        verify(userRepository, times(1)).findById(200l);
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * Test create user success
     * API: POST /users
     */
    @Test
    public void testCreateUserSuccess() throws Exception {
        log.debug("Test create user success API /user");
        User user = new User(200, "danh@gmail.com", "David Nguyen");
        UserDTO userDTO = new UserDTO(user);
        when(userRepository.findByEmail("danh@gmail.com")).thenReturn(null);
        // Can not pass object user to method, must use Class to represent
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is(user.getEmail())))
            .andExpect(jsonPath("$.lastName", is(user.getLastName())));

        verify(userRepository, times(1)).findByEmail("danh@gmail.com");
        verify(userRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * Test update user success
     * API: /user{id}
     */
    @Test
    public void testUpdateUserSuccess() throws Exception {
        User user = new User(200, "danh@gmail.com", "David Nguyen");
        UserDTO userDTO = new UserDTO(user);
        when(userRepository.findById(200l)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(
            put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is(user.getEmail())))
            .andExpect(jsonPath("$.lastName", is(user.getLastName())));

        verify(userRepository, times(1)).findById(200l);
        verify(userRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * Test update user fail method argument not valid
     */
    @Test
    public void testUpdateUserWithShortNameFailMethodArgument() throws Exception {
        User user = new User(200,"danh@gmail.com", "David");
        UserDTO userDTO = new UserDTO(user);

        mockMvc.perform(
            put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
            .andExpect(status().isBadRequest())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.code", is(CustomError.BAD_REQUEST.code())))
            .andExpect(jsonPath("$.message", is(CustomError.BAD_REQUEST.message())));
            // FIXME: Get message from application.properties
            // .andExpect(jsonPath("$.message", is(CustomError.NOT_FOUND_USER.message())));
    }

    /**
     * Test update user fail user not found
     */
    @Test
    public void testUpdateUserFailUserNotFound() throws Exception {
        User user = new User(200,"danh@gmail.com", "David Nguyen");
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(null));
        UserDTO userDTO = new UserDTO(user);

        mockMvc.perform(
            put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", is(CustomError.NOT_FOUND_USER.code())))
            .andExpect(jsonPath("$.message", is(CustomError.NOT_FOUND_USER.message())));
    }

    /**
     * Test delete user success
     */
    @Test
    public void testDeleteUserSuccess() throws Exception {
        User user = new User(200, "danh@gmail.com", "David Nguyen");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(user.getId());

        mockMvc.perform(
            delete("/users/{id}", user.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is(user.getEmail())))
            .andExpect(jsonPath("$.lastName", is(user.getLastName())));

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
        verifyNoMoreInteractions(userRepository);
    }

    /**
     *  Test headers get all user
     */
    @Test
    public void testHeaderResponseGetAllUser() throws Exception {
        mockMvc.perform(get("/users"))
            .andExpect(header().string("content-type", "application/json;charset=UTF-8"));
    }

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

    /**
     * Write an object into JSON representation
     */
    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
