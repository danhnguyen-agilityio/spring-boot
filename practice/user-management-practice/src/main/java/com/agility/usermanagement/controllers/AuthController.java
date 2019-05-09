package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dto.UserRequest;
import com.agility.usermanagement.dto.UserResponse;
import com.agility.usermanagement.exceptions.BadAccountCredentialException;
import com.agility.usermanagement.exceptions.ResourceAlreadyExistsException;
import com.agility.usermanagement.mappers.UserMapper;
import com.agility.usermanagement.models.User;
import com.agility.usermanagement.securities.AuthenticationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.agility.usermanagement.exceptions.CustomError.BAD_CREDENTIALS;
import static com.agility.usermanagement.exceptions.CustomError.USERNAME_ALREADY_EXISTS;

@RestController
@RequestMapping("/v1/auths")
public class AuthController extends BaseController {

    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    public AuthController(PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    /**
     * Sign in app
     *
     * @param credential Authentication request
     * @return Response with token attached in header
     * @throws BadAccountCredentialException if invalid credential or returned user null id
     */
    @PostMapping("/signin")
    public ResponseEntity signin(@Valid @RequestBody AuthenticationRequest credential) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credential.getUsername(),
                credential.getPassword()));

            User user = userRepository.findByUsername(credential.getUsername())
                .orElseThrow(null);

            String token = jwtTokenService.createToken(user);

            if (token == null) {
                throw new BadAccountCredentialException(BAD_CREDENTIALS);
            }

            return ResponseEntity.ok().header(securityConfig.getHeaderString(), token).body(null);
        } catch (BadCredentialsException e) {
            throw new BadAccountCredentialException(BAD_CREDENTIALS);
        }
    }


    /**
     * Sign up app
     *
     * @param userRequest Authentication request
     * @return created user
     * @throws ResourceAlreadyExistsException when username already exists in system
     */
    @PostMapping("/signup")
    public UserResponse signup(@Valid @RequestBody UserRequest userRequest) {
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
}
