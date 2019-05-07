package com.agility.usermanagement.mappers;

import com.agility.usermanagement.constants.RoleName;
import com.agility.usermanagement.dto.UserResponse;
import com.agility.usermanagement.models.Role;
import com.agility.usermanagement.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserMapperTest {

    private User user;
    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Before
    public void setUp() {
        user = new User();
        user.setId(100L);
        user.setUsername("danhnguyen");
        user.setFirstName("david");
        user.setLastName("nguyen");
        user.setAddress("Tam ky quang nam");
        user.setActive(true);
        List<Role> roles = Arrays.asList(new Role(1L, RoleName.ADMIN), new Role(1L, RoleName.USER));
        user.setRoles(roles);
    }

    @Test
    public void testMapperToUserResponse() {
        UserResponse userResponse = mapper.toUserResponse(user);

        assertEquals(user.getId(), userResponse.getId());
        assertEquals(user.getUsername(), userResponse.getUsername());
        assertEquals(user.getFirstName(), userResponse.getFirstName());
        assertEquals(user.getLastName(), userResponse.getLastName());
        assertEquals(user.getAddress(), userResponse.getAddress());
        assertEquals(2, userResponse.getRoles().size());
        assertEquals(RoleName.ADMIN.name(), userResponse.getRoles().get(0));
        assertEquals(RoleName.USER.name(), userResponse.getRoles().get(1));
    }

}