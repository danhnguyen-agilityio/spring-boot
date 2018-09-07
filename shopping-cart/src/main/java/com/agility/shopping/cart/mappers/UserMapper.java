package com.agility.shopping.cart.mappers;

import com.agility.shopping.cart.dto.UserResponse;
import com.agility.shopping.cart.models.User;
import org.mapstruct.Mapper;

/**
 * UserMapper interface is used to map between different object models
 * that relate to user
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert from user to user response
     *
     * @param user User need convert
     * @return User response
     */
    UserResponse toUserResponse(User user);
}
