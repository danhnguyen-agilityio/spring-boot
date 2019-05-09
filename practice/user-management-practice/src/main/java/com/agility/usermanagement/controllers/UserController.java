package com.agility.usermanagement.controllers;

import com.agility.usermanagement.models.Role;
import com.agility.usermanagement.dto.UserRequest;
import com.agility.usermanagement.dto.UserResponse;
import com.agility.usermanagement.dto.UserUpdate;
import com.agility.usermanagement.exceptions.BadRequestException;
import com.agility.usermanagement.exceptions.ResourceAlreadyExistsException;
import com.agility.usermanagement.exceptions.ResourceNotFoundException;
import com.agility.usermanagement.mappers.UserMapper;
import com.agility.usermanagement.models.User;
import com.agility.usermanagement.services.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Arrays;
import java.util.List;

import static com.agility.usermanagement.exceptions.CustomError.*;

@RestController
public class UserController extends BaseController {

    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    private UserService userService;

    public UserController(UserMapper userMapper, PasswordEncoder passwordEncoder, UserService userService) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    /**
     * Get self user info
     *
     * @param userDetails authentication credential
     * @return user data
     */
    @GetMapping("/me")
    public UserResponse currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        UserResponse userResponse = userMapper.toUserResponse(user);
        return userResponse;
    }

    /**
     * Update self user info
     *
     * @param userDetails authentication credential
     * @param userUpdate data that client sent
     * @return user data
     */
    @PostMapping("/me")
    public UserResponse updateInfo(@AuthenticationPrincipal UserDetails userDetails,
                                   @Valid @RequestBody UserUpdate userUpdate) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setAddress(userUpdate.getAddress());

        user = userRepository.save(user);
        UserResponse userResponse = userMapper.toUserResponse(user);
        return userResponse;
    }

    /**
     * Get all users
     *
     * @return all users
     */
    @GetMapping("/users")
    @Secured({Role.ROLE_ADMIN, Role.ROLE_MANAGER})
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();

        return userMapper.toUserResponses(users);
    }

    /**
     * Create user (Only manager user and admin user have permission for feature)
     *
     * @return created user
     * @throws ResourceAlreadyExistsException if user name exists
     */
    @PostMapping("/users")
    @Secured({Role.ROLE_ADMIN, Role.ROLE_MANAGER})
    public UserResponse create(@Valid @RequestBody UserRequest userRequest) {
        User user = userRepository.findByUsername(userRequest.getUsername()).orElse(null);

        // User already exists
        if (user != null) {
            throw new ResourceAlreadyExistsException(USERNAME_ALREADY_EXISTS);
        }

        // User not already exists
        user = new User();
        user.setUsername(userRequest.getUsername());
        user.setUsername(passwordEncoder.encode(userRequest.getPassword()));
        user.setActive(true);
        user.setRoles(Arrays.asList(Role.USER));

        User savedUser = userRepository.save(user);
        UserResponse userResponse = userMapper.toUserResponse(savedUser);

        return userResponse;
    }

    /**
     * Delete user (Only manager user and admin user have permission for feature)
     */
    @DeleteMapping("/users/{id}")
    @Secured({Role.ROLE_ADMIN, Role.ROLE_MANAGER})
    public void delete(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        userRepository.delete(user);
    }

    /**
     * Change active user (Only manager user and admin user have permission for feature)
     */
    @PutMapping("/users/{id}/activate")
    @Secured({Role.ROLE_ADMIN, Role.ROLE_MANAGER})
    public UserResponse updateActive(@Valid @RequestBody UserUpdate userUpdate, @PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        user.setActive(userUpdate.isActive());

        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponse(updatedUser);
    }

    /**
     * Update role user (Only admin user have permission for feature)
     */
    @PutMapping("/users/{id}/roles")
    @Secured({Role.ROLE_ADMIN})
    public UserResponse updateRole(@Valid @RequestBody UserUpdate userUpdate, @PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        user.getRoles().clear();
        switch (userUpdate.getRole()) {
            case "USER":
                user.getRoles().add(Role.USER);
                break;
            case "MANAGER":
                user.getRoles().add(Role.USER);
                user.getRoles().add(Role.MANAGER);
                break;
            case "ADMIN":
                user.getRoles().add(Role.USER);
                user.getRoles().add(Role.MANAGER);
                user.getRoles().add(Role.ADMIN);
                break;
            default:
                throw new BadRequestException(INVALID_ROLE_NAME);
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponse(updatedUser);
    }

}
