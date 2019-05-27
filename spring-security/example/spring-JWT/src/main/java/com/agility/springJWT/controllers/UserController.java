package com.agility.springJWT.controllers;

import com.agility.springJWT.models.User;
import com.agility.springJWT.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/sign-up")
    public User signUp(@RequestBody User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userService.saveUser(user);
        return user;
    }

    @GetMapping("/admin")
    public User admin() {
        return userService.getUserByUsername("admin");
    }

    @GetMapping("/user")
    public User user() {
        return userService.getUserByUsername("user");
    }

    @GetMapping("/authority-no-prefix")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User useAuthorityWithDatabaseNotContainPrefix() {
        return userService.getUserByUsername("admin");
    }

    @GetMapping("/authority")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User useAuthority() {
        return userService.getUserByUsername("admin");
    }

    @GetMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    public User useRole() {
        return userService.getUserByUsername("admin");
    }

    @GetMapping("/test2")
    public User test2(HttpServletRequest request) {
        logger.info("API /test2");
        if (request.isUserInRole("ADMIN")) {
            Principal userPrincipal = request.getUserPrincipal();
            logger.info("Name: {}", userPrincipal.getName());
             return new User("ADMIN", "ADMIN", "ADMIN");

//            logger.info("Groups: {} ", request.getAttribute("groups"));
//            ArrayList<String> groups = (ArrayList<String>) request.getAttribute("groups");
//            logger.info("part of group: {}", groups);
//            for (String s : groups) {
//                logger.info("part of group: {}", s);
//            }
        }

        return new User("USER", "USER", "USER");
    }
}
