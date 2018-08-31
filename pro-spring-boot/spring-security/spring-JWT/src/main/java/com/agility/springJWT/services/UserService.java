package com.agility.springJWT.services;

import com.agility.springJWT.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * UserService class is used to managed the user stored in the database and link it to the security
 */
@Service
public class UserService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private static final List<User> users = new ArrayList<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("UserService");

        User user = getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), "password", Collections.emptyList());
    }

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

    public List<String> getRoles(String username) {
        System.out.println("UserService getRoles() is called");
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        if ("admin".equals(username)) {
            roles.add("ADMIN");
        }

        return roles;
    }
}
