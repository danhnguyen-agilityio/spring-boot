package com.agility.usermanagement.services;

import com.agility.usermanagement.dtos.AppUserResponse;
import com.agility.usermanagement.dtos.UserUpdatedRequest;
import com.agility.usermanagement.exceptions.InternalServerException;
import com.agility.usermanagement.exceptions.ResourceAlreadyExistsException;
import com.agility.usermanagement.exceptions.ResourceNotFoundException;
import com.agility.usermanagement.mappers.UserMapper;
import com.agility.usermanagement.models.AppUser;
import com.agility.usermanagement.models.Role;
import com.agility.usermanagement.repositories.UserRepository;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.group.Group;
import com.okta.sdk.resource.group.GroupList;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.agility.usermanagement.exceptions.CustomError.EMAIL_ALREADY_EXISTS;
import static com.agility.usermanagement.exceptions.CustomError.INTERNAL_SERVER_ERROR;
import static com.agility.usermanagement.exceptions.CustomError.USER_NOT_FOUND;

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
        appUser.setActive("ACTIVE".equalsIgnoreCase(user.getStatus().name()));
        appUser.setRoles(Arrays.asList(Role.USER));

        appUser = userRepository.save(appUser);

        return userMapper.toAppUserResponse(appUser);
    }

    /**
     * Find user given id
     *
     * @param id Id of user
     * @return AppUserResponse object that match given id
     */
    public AppUserResponse findById(String id) {
        AppUser appUser = userRepository.findById(id).orElse(null);

        if (appUser == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        return userMapper.toAppUserResponse(appUser);
    }

    /**
     * Find all user
     *
     * @return All user
     */
    public List<AppUserResponse> findAll() {
        return userMapper.toAppUserResponseList(userRepository.findAll());
    }

    /**
     * Find user by username
     *
     * @param username Name of user
     * @return AppUserResponse object with name matched given username
     */
    public AppUserResponse findByUsername(String username) {
        AppUser appUser = userRepository.findByEmail(username);
        return userMapper.toAppUserResponse(appUser);
    }

    /**
     * Update info by username
     *
     * @param request Info need updated
     * @return Updated AppUserResponse object
     */
    public AppUserResponse updateInfoByUsername(UserUpdatedRequest request) {
        AppUser appUser = userRepository.findByEmail(request.getEmail());

        if (appUser == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        appUser.setFirstName(request.getFirstName());
        appUser.setLastName(request.getLastName());

        appUser = userRepository.save(appUser);

        return userMapper.toAppUserResponse(appUser);
    }

    /**
     * Active user
     *
     * @param id Id of user need active or deactive
     * @param active flag represent status active or deactive
     * @return AppUserResponse object
     */
    public AppUserResponse activeUser(String id, boolean active) {
        AppUser appUser = userRepository.findById(id).orElse(null);
        if (appUser == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        User user = client.getUser(id);
        if (user == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        try {
            // save to own database
            appUser.setActive(active);
            appUser = userRepository.save(appUser);

            // save to okta server
            user.deactivate(!active);
        } catch (Exception e) {
            throw new InternalServerException(INTERNAL_SERVER_ERROR);
        }

        return userMapper.toAppUserResponse(appUser);
    }

    /**
     * Delete user by given id
     *
     * @param id Id of user need delete
     */
    public void deleteById(String id) {
        AppUser appUser = userRepository.findById(id).orElse(null);
        if (appUser == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        User user = client.getUser(id);
        if (user == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        try {
            // delete user from okta server
            user.delete();

            // delete user from own database
            userRepository.delete(appUser);
        } catch (Exception e) {
            throw new InternalServerException(INTERNAL_SERVER_ERROR);
        }
    }
}
