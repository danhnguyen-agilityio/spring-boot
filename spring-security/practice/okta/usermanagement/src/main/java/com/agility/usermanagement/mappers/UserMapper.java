package com.agility.usermanagement.mappers;

import com.agility.usermanagement.dtos.AppUserResponse;
import com.agility.usermanagement.dtos.UserCreatedRequest;
import com.agility.usermanagement.models.AppUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert UserCreatedRequest object to AppUser object
     *
     * @param request object need converted
     * @return app user object
     */
    AppUser toAppUser(UserCreatedRequest request);

    /**
     * Convert AppUser object to AppUserResponse object
     *
     * @param appUser object need converted
     * @return app user response object
     */
    AppUserResponse toAppUserResponse(AppUser appUser);


    /**
     * Convert list AppUser object to list AppUserResponse object
     *
     * @param appUsers list object need converted
     * @return  list app user response object
     */
    List<AppUserResponse> toAppUserResponseList(List<AppUser> appUsers);
}
