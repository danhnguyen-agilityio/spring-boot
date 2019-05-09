package com.agility.usermanagement.mappers;

import com.agility.usermanagement.constants.RoleName;
import com.agility.usermanagement.dto.UserResponse;
import com.agility.usermanagement.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserMapperTest {

    private User user;
    private List<User> users;
    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Before
    public void setUp() {
        // fake user
        user = new User();
        user.setId(100L);
        user.setUsername("danhnguyen");
        user.setFirstName("david");
        user.setLastName("nguyen");
        user.setAddress("Tam ky quang nam");
        user.setActive(true);
        List<RoleName> roles = Arrays.asList(RoleName.ADMIN, RoleName.USER);
        user.setRoles(roles);

        // fake user list
        users = Arrays.asList(new User(1L, "danhnguyentk"), new User(2L, "tunguyentk"));
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

    @Test
    public void testMapperToListUserResponse() {
        List<UserResponse> userResponses = mapper.toUserResponses(users);

        assertEquals(userResponses.size(), users.size());
        assertEquals(userResponses.get(0).getId(), users.get(0).getId());
        assertEquals(userResponses.get(1).getId(), users.get(1).getId());
        assertEquals(userResponses.get(0).getUsername(), users.get(0).getUsername());
        assertEquals(userResponses.get(1).getUsername(), users.get(1).getUsername());
    }
}