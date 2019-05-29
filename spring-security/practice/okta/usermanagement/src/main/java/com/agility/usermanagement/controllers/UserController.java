package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dtos.AppUserResponse;
import com.agility.usermanagement.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('Manager', 'Admin')")
    public List<AppUserResponse> findAll() {
        return userService.findAll();
    }
    
}
