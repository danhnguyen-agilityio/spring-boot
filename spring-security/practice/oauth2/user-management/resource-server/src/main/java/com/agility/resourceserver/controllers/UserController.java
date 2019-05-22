package com.agility.resourceserver.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    /**
     * Get self user info
     *
     * @return user data
     */
    @GetMapping("/me")
    public String currentUser(Principal principal) {
        return principal.getName();
    }
}
