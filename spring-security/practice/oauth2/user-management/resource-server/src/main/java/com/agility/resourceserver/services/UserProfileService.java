package com.agility.resourceserver.services;

import com.agility.resourceserver.dto.UserProfileResponse;
import com.agility.resourceserver.exceptions.ResourceNotFoundException;
import com.agility.resourceserver.mappers.UserProfileMapper;
import com.agility.resourceserver.models.UserProfile;
import com.agility.resourceserver.repositorys.UserProfileRepository;
import org.springframework.stereotype.Service;

import static com.agility.resourceserver.exceptions.CustomError.USER_NOT_FOUND;

@Service
public class UserProfileService {

    private UserProfileMapper userProfileMapper;
    private UserProfileRepository userProfileRepository;


    public UserProfileService(UserProfileMapper userProfileMapper, UserProfileRepository userProfileRepository) {
        this.userProfileMapper = userProfileMapper;
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfileResponse findByUsername(String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username);

        if (userProfile == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        UserProfileResponse userProfileResponse = userProfileMapper.toUserProfileResponse(userProfile);
        return userProfileResponse;
    }
}
