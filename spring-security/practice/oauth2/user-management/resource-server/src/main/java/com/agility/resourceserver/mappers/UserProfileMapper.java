package com.agility.resourceserver.mappers;

import com.agility.resourceserver.dto.UserProfileResponse;
import com.agility.resourceserver.models.UserProfile;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * UserProfileMapper interface is used to map between different object models that relate to UserProfile
 */
@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    /**
     * Convert from user to user response
     *
     * @param userProfile User need convert
     * @return User profile response
     */
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);

    /**
     * Convert from user to user response
     *
     * @param userProfiles User need convert
     * @return List User profile
     */
    List<UserProfileResponse> toUserProfileResponses(List<UserProfile> userProfiles);
}
