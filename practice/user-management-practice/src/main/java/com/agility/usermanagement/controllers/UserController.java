package com.agility.usermanagement.controllers;

import com.agility.usermanagement.constants.RoleName;
import com.agility.usermanagement.dto.UserRequest;
import com.agility.usermanagement.dto.UserResponse;
import com.agility.usermanagement.dto.UserUpdate;
import com.agility.usermanagement.exceptions.BadAccountCredentialException;
import com.agility.usermanagement.exceptions.ResourceAlreadyExistsException;
import com.agility.usermanagement.exceptions.ResourceNotFoundException;
import com.agility.usermanagement.mappers.UserMapper;
import com.agility.usermanagement.models.User;
import com.agility.usermanagement.securities.RoleConstant;
import com.agility.usermanagement.services.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.agility.usermanagement.exceptions.CustomError.BAD_CREDENTIALS;
import static com.agility.usermanagement.exceptions.CustomError.USERNAME_ALREADY_EXISTS;
import static com.agility.usermanagement.exceptions.CustomError.USER_NOT_FOUND;

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
    @Secured({RoleConstant.ADMIN, RoleConstant.MANAGER})
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
    @Secured({RoleConstant.ADMIN, RoleConstant.MANAGER})
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

        User savedUser = userRepository.save(user);
        UserResponse userResponse = userMapper.toUserResponse(savedUser);

        return userResponse;
    }

    /**
     * Delete user (Only manager user and admin user have permission for feature)
     */
    @DeleteMapping("/users/{id}")
    @Secured({RoleConstant.ADMIN, RoleConstant.MANAGER})
    public void delete(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        userRepository.delete(user);
    }

    /**
     * Update user (Only manager user and admin user have permission for feature)
     */
    @PutMapping("/users/{id}/activate")
    @Secured({RoleConstant.ADMIN, RoleConstant.MANAGER})
    public UserResponse updateActive(@AuthenticationPrincipal UserDetails userDetails,
                     @Valid @RequestBody UserUpdate userUpdate,
                     @PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        user.setActive(userUpdate.isActive());

        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponse(updatedUser);
    }

}
