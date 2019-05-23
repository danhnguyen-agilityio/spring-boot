package com.agility.authorizationserver.controllers;

import com.agility.authorizationserver.dto.AuthRequest;
import com.agility.authorizationserver.dto.UserResponse;
import com.agility.authorizationserver.exceptions.ResourceAlreadyExistsException;
import com.agility.authorizationserver.mappers.UserMapper;
import com.agility.authorizationserver.models.Role;
import com.agility.authorizationserver.models.User;
import com.agility.authorizationserver.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Arrays;

import static com.agility.authorizationserver.exceptions.CustomError.USERNAME_ALREADY_EXISTS;

@RestController
@RequestMapping("/auths")
public class AuthController {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public AuthController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody AuthRequest authRequest) {
        User  user = userRepository.findByUsername(authRequest.getUsername()).orElse(null);

        // User already exists
        if (user != null) {
            throw new ResourceAlreadyExistsException(USERNAME_ALREADY_EXISTS);
        }

        // User not already exists
        user = User.builder()
            .username(authRequest.getUsername())
            .password(authRequest.getPassword())
            .roles(Arrays.asList(Role.USER))
            .build();

        User savedUser = userRepository.save(user);
        UserResponse userResponse = userMapper.toUserResponse(savedUser);

        return userResponse;
    }
}
