package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dtos.AppUserResponse;
import com.agility.usermanagement.dtos.UserCreatedRequest;
import com.agility.usermanagement.mappers.UserMapper;
import com.agility.usermanagement.models.AppUser;
import com.agility.usermanagement.services.UserService;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/public")
public class PublicController {

    private Client client;
    private UserMapper userMapper;
    private UserService userService;

    public PublicController(Client client, UserMapper userMapper, UserService userService) {
        this.client = client;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public AppUserResponse signup(@Valid @RequestBody UserCreatedRequest request) {
        log.debug("POST /api/v1/public/signup body={}", request);

        AppUser appUser = userMapper.toAppUser(request);

        return userService.createUser(appUser);
    }
}
