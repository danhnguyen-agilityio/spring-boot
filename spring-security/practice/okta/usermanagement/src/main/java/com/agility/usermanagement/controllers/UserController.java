package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dtos.AppUserResponse;
import com.agility.usermanagement.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
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

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('Manager', 'Admin')")
    public AppUserResponse findById(String id) {
        return userService.findById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('User', 'Manager', 'Admin')")
    public AppUserResponse currentUser(Principal principal) {
        log.debug("/api/v1/me username={}", principal.getName());

        String username = principal.getName();

        return userService.findByUsername(username);
    }
}
