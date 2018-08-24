package com.agility.spring.controllers;

import com.agility.spring.dto.UserDTO;
import com.agility.spring.exceptions.BadRequestException;
import com.agility.spring.exceptions.CustomError;
import com.agility.spring.exceptions.NotFoundException;
import com.agility.spring.models.User;
import com.agility.spring.repositorys.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User createUser(@RequestParam("email") String email,
                           @RequestParam("name") String name) {
        User user;
        try {
            user = new User(email, name);
            userRepository.save(user);
        } catch (Exception ex) {
            return null;
        }
        return user;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
        consumes = "application/json;version=2")
    public String getUserV2(@PathVariable("userId") long id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            return "User have email: " + user.getEmail();
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET,
        headers = {"X-API-Version=3"})
    public String getUserV3(@PathVariable("userId") long id) {
        return "This is api version 3 get user by id ";
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    /**
     * Get user by id
     *
     * @param userId Id of user
     * @return UserDTO
     *
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
     * Add user
     *
     * @param userDTO Request body
     * @return UserDTO
     */
    @RequestMapping(method = RequestMethod.POST)
    public UserDTO addUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        userRepository.save(user);
        return new UserDTO(user);
    }

    /**
     * Update user by id
     *
     * @param userId  Id of user
     * @param userDTO Request body
     * @return UserDTO
     * @throws NotFoundException   occur if id of user not exist
     * @throws BadRequestException occur if not have info email
     */
    @PutMapping(value = "/{userId}")
    public UserDTO updateUser(@PathVariable long userId,
                              @RequestBody UserDTO userDTO) {
        logger.debug("PUT /v1/users/{}, body={}", userId, userDTO);

        // Find user from DB
        User user = userRepository.findById(userId).orElse(null);

        // Throw exception NOT_FOUND_USER if user not exist
        if (user == null)
            throw new NotFoundException(CustomError.NOT_FOUND_USER);

        // Throw exception BAD_REQUEST if body request not have info email
        if (userDTO.getEmail() == null)
            throw new BadRequestException(CustomError.BAD_REQUEST);

        // Set new info for user
        user.setId(userId);
        user.setEmail(userDTO.getEmail());
        user.setLastName(userDTO.getLastName());

        // Save user to database and get user return
        User updatedUser = userRepository.save(user);

        logger.debug("Response {}", updatedUser);
        // Convert to UserDTO and return it
        return new UserDTO(updatedUser);
    }

    /** Delete user
     *
     * @param userId Id of user
     * @return UserDTO
     * @throws NotFoundException throw when if of user not exits
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public UserDTO deleteUser(@PathVariable long userId) {

        // Get user by id
        User user = userRepository.findById(userId).orElse(null);

        // Throw exception NOT_FOUND_USER if user not exist
        if (user == null)
            throw new NotFoundException(CustomError.NOT_FOUND_USER);

        // Delete user
        userRepository.deleteById(userId);

        // Convert to UserDTO and return it
        return new UserDTO(user);
    }

    /**
     * User PUT method via POST method to update user
     *
     * @param userId Id of user
     * @param userDTO Request body
     * @return UserDTO
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST,
        headers = {"X-HTTP-Method-Override=PUT"})
    public UserDTO updateUserAsPost(@PathVariable("userId") long userId,
                                    @RequestBody UserDTO userDTO) {
        logger.info("Use PUT method via POST method");
        return updateUser(userId, userDTO);
    }

}
