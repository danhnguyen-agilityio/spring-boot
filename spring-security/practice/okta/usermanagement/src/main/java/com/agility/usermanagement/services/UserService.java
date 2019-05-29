package com.agility.usermanagement.services;

import com.agility.usermanagement.dtos.AppUserResponse;
import com.agility.usermanagement.exceptions.InternalServerException;
import com.agility.usermanagement.exceptions.ResourceAlreadyExistsException;
import com.agility.usermanagement.mappers.UserMapper;
import com.agility.usermanagement.models.AppUser;
import com.agility.usermanagement.models.Role;
import com.agility.usermanagement.repositories.UserRepository;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.group.Group;
import com.okta.sdk.resource.group.GroupList;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static com.agility.usermanagement.exceptions.CustomError.EMAIL_ALREADY_EXISTS;
import static com.agility.usermanagement.exceptions.CustomError.INTERNAL_SERVER_ERROR;

@Service
public class UserService {

    private Client client;
    private UserRepository userRepository;
    private GroupService groupService;
    private UserMapper userMapper;

    public UserService(Client client, UserRepository userRepository, GroupService groupService, UserMapper userMapper) {
        this.client = client;
        this.userRepository = userRepository;
        this.groupService = groupService;
        this.userMapper = userMapper;
    }

    /**
     * Create new user
     *
     * @param appUser app user entity
     * @return app user response object
     * @throws InternalServerException if get group info error
     */
    public AppUserResponse createUser(AppUser appUser) {
        AppUser foundAppUser = userRepository.findByEmail(appUser.getEmail());

        if (foundAppUser != null) {
            throw new ResourceAlreadyExistsException(EMAIL_ALREADY_EXISTS);
        }

        GroupList groupList = groupService.find(Role.USER.getName());
        Group group = groupList.single();

        if (group == null) {
            throw new InternalServerException(INTERNAL_SERVER_ERROR);
        }

        User user = UserBuilder.instance()
            .setEmail(appUser.getEmail())
            .setFirstName(appUser.getFirstName())
            .setLastName(appUser.getLastName())
            .setPassword(appUser.getPassword().toCharArray())
            .setGroups(new HashSet<>(Collections.singleton(group.getId())))
            .setActive(true) // FIXME:: Create Debugger flag, value is received from properties
            .buildAndCreate(client);

        appUser.setId(user.getId());
        appUser.setRoles(Arrays.asList(Role.USER));

        appUser = userRepository.save(appUser);

        return userMapper.toAppUserResponse(appUser);
    }
}
