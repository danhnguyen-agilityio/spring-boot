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

import java.util.Arrays;

/**
 * UserService class is used to managed the user stored in the database and link it to the security
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("AppUserDetailsService");

        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        // Consider about should get authority in here
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole());

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), "password", Arrays.asList(grantedAuthority)
        );
}
}
