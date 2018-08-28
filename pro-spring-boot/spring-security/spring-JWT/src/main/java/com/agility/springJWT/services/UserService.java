package com.agility.springJWT.services;

import com.agility.springJWT.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public static final Logger log = LoggerFactory.getLogger(UserService.class);

    List<User> users = new ArrayList<>();

    public User getUserByUsername(String username) {
        if ("admin".equals(username)) {
            return new User("admin", "password", "ADMIN");
        }

        if ("user".equals(username)) {
            return new User("user", "password", "USER");
        }

        return null;
    }

    public void saveUser(User user) {
        users.add(user);
        log.info("Add user: {}", user);
    }
}
