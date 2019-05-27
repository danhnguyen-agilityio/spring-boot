package com.agility.authorizationserver.services;

import com.agility.authorizationserver.models.User;
import com.agility.authorizationserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        log.debug("User from database: {}", user);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return user;
    }
}
