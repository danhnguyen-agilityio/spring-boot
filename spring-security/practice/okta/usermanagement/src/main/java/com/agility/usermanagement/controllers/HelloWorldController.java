package com.agility.usermanagement.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class HelloWorldController {

    @CrossOrigin(origins = "http://api.jquery.com")
    @GetMapping("/")
    public String getMethod() {
        return "Hello world";
    }

    @GetMapping("/cors")
    public String cors() {
        return "Hello world";
    }

    @PostMapping("/")
    public String postMethod() {
        return "Hello world";
    }


    @PutMapping("/")
    public String putMethod() {
        return "Hello world";
    }

    @DeleteMapping("/")
    public String deleteMethod() {
        return "Hello world";
    }

    @GetMapping("/protected")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public String helloWorldProtected(Principal principal) {
        return "Hello VIP " + principal.getName();
    }

    @GetMapping("/manager")
    @PreAuthorize("hasAuthority('Manager')")
    public String manager() {
        return "manager";
    }
}
