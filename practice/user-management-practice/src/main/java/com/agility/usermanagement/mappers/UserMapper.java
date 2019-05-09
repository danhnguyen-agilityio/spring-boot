package com.agility.usermanagement.mappers;

import com.agility.usermanagement.constants.Role;
import com.agility.usermanagement.dto.UserResponse;
import com.agility.usermanagement.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserMapper interface is used to map between different object models that relate to User
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert from user to user response
     *
     * @param user User need convert
     * @return User response
     */
    @Mapping(source = "user", target = "roles")
    UserResponse toUserResponse(User user);

    default List<String> toRoles(User user) {
        List<String> roles = user.getRoles().stream()
            .map(Role::name)
            .collect(Collectors.toList());

        return roles;
    }

    /**
     * Convert from user to user response
     *
     * @param users User need convert
     * @return User response
     */
    List<UserResponse> toUserResponses(List<User> users);
}
