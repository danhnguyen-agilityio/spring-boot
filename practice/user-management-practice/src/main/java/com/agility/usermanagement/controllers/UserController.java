package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dto.UserResponse;
import com.agility.usermanagement.dto.UserUpdate;
import com.agility.usermanagement.exceptions.ResourceNotFoundException;
import com.agility.usermanagement.mappers.UserMapper;
import com.agility.usermanagement.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.agility.usermanagement.exceptions.CustomError.USER_NOT_FOUND;

@RestController
public class UserController extends BaseController {

    private UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
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
}
