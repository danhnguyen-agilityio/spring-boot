package com.agility.spring.controllers;

import com.agility.spring.dto.UserDTO;
import com.agility.spring.exceptions.BadRequestException;
import com.agility.spring.exceptions.CustomError;
import com.agility.spring.exceptions.NotFoundException;
import com.agility.spring.models.User;
import com.agility.spring.repositorys.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * The class create REST Api for User
 *
 * @author Danh Nguyen
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;

    /**
     * Constructor with user repository
     */
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get all user
     *
     * @return List user in database
     */
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Get all user use specific header on request
     * Header with key = X-API-Version, value = 3
     *
     * @return List user in database
     */
    @GetMapping(value = "v1", headers = {"X-API-Version=3"})
    public List<User> getAllUserUseHeaderV1() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    /**
     * Get all user use specific header on request
     * Header with key = consumes, value = application/json;version=2
     *
     * @return List user in database
     */
    @GetMapping(value = "/v2", consumes = "application/json;version=2")
    public List<User> getAllUserUseHeaderV2() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    /**
     * Get user by given identify
     *
     * @param userId Id of user
     * @return User info with given identify
     * @throws NotFoundException if user not exist in database
     */
    @GetMapping(value = "/{userId}")
    public UserDTO getUser(@PathVariable("userId") long userId) {

        // Find user from database
        User user = userRepository.findById(userId).orElse(null);

        // Throw NotFoundException if user not exist
        if (user == null) {
            throw new NotFoundException(CustomError.NOT_FOUND_USER);
        }

        // Convert to UserDTO and return it
        return new UserDTO(user);
    }

    /**
     * Create user with email and name
     *
     * @param userDTO
     * @throws BadRequestException if email exist in system
     * @return
     */
    @PostMapping
    public UserDTO create(@RequestBody UserDTO userDTO) {
        log.debug("Create User: POST /users");

        User userResponse;
        User user = userRepository.findByEmail(userDTO.getEmail());

        if (user == null) {
            User userRequest = new User(userDTO);
            log.debug("UserRequest: {}", userRequest);
            userResponse = userRepository.save(userRequest);
        } else {
            throw new BadRequestException(CustomError.EXIST_EMAIL);
        }

        log.debug("Created user: {}", userResponse);

        return new UserDTO(userResponse);
    }

    /**
     * Create user with email and name
     *
     * @param email Email of user
     * @param name  Name of user
     * @throws BadRequestException if email exist in system
     * @return
     */
    @PostMapping("/create")
    public UserDTO createUser(@RequestParam("email") String email,
                           @RequestParam("name") String name) {
        log.debug("POST /create");

        User userResponse;
        User user = userRepository.findByEmail(email);

        if (user == null) {
            User userRequest = new User(email, name);
            userResponse = userRepository.save(userRequest);
        } else {
            throw new BadRequestException(CustomError.EXIST_EMAIL);
        }

        log.debug("Created user: {}", userResponse);

        return new UserDTO(userResponse);
    }

    /**
     * Updates user with the given identifier
     *
     * @param userId  The user id to update
     * @param userDTO User info from request body
     * @return the user with the given ID
     * @throws NotFoundException   if id of user not exist
     * @throws BadRequestException if not have info email
     */
    @PutMapping(value = "/{userId}")
    public UserDTO updateUser(@PathVariable long userId,
                              @Valid @RequestBody UserDTO userDTO) {
        log.debug("PUT /users/{}, body={}", userId, userDTO);

        // Find user from DB
        User user = userRepository.findById(userId).orElse(null);

        // Throw exception NOT_FOUND_USER
        // if user not exist
        if (user == null)
            throw new NotFoundException(CustomError.NOT_FOUND_USER);

        // Throw exception BAD_REQUEST
        // if body request not have info email
        if (userDTO.getEmail() == null)
            throw new BadRequestException(CustomError.BAD_REQUEST);

        // Set new info for user
        user.setId(userId);
        user.setEmail(userDTO.getEmail());
        user.setLastName(userDTO.getLastName());

        // Save user to database
        User updatedUser = userRepository.save(user);

        log.debug("Response {}", updatedUser);
        // Convert to UserDTO
        // and return it
        return new UserDTO(updatedUser);
    }

    /**
     * Delete user
     *
     * @param userId Id of user
     * @return Returns user info
     * @throws NotFoundException if user with specify id not exist in database
     */
    @DeleteMapping("/{userId}")
    public UserDTO deleteUser(@PathVariable long userId) {

        // Get user by id
        User user = userRepository.findById(userId).orElse(null);

        // Throw exception NOT_FOUND_USER
        // if user not exist
        if (user == null)
            throw new NotFoundException(CustomError.NOT_FOUND_USER);

        // Delete user
        userRepository.deleteById(userId);

        // Convert to UserDTO
        // and return it
        return new UserDTO(user);
    }

    /**
     * User PUT method via POST method to update user
     *
     * @param userId  Id of user
     * @param userDTO Request body
     * @return Returns user info
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST,
        headers = {"X-HTTP-Method-Override=PUT"})
    public UserDTO updateUserAsPost(@PathVariable("userId") long userId,
                                    @RequestBody UserDTO userDTO) {
        log.debug("Use PUT method via POST method");
        return updateUser(userId, userDTO);
    }

}
