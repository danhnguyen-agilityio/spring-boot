package com.agility.resourceserver.controllers;

import com.agility.resourceserver.dto.UserProfileResponse;
import com.agility.resourceserver.exceptions.ResourceNotFoundException;
import com.agility.resourceserver.mappers.UserProfileMapper;
import com.agility.resourceserver.models.UserProfile;
import com.agility.resourceserver.repositorys.UserProfileRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.agility.resourceserver.exceptions.CustomError.USER_NOT_FOUND;

@RestController
public class UserController {

    private UserProfileRepository userProfileRepository;
    private UserProfileMapper userProfileMapper;

    public UserController(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }

    /**
     * Get self user info
     *
     * @return user data
     */
    @GetMapping("/me")
    public UserProfileResponse currentUser(Principal principal) {
        String username = principal.getName();
        UserProfile userProfile = userProfileRepository.findByUsername(username);

        if (userProfile == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        UserProfileResponse userProfileResponse = userProfileMapper.toUserProfileResponse(userProfile);
        return userProfileResponse;
    }
}
