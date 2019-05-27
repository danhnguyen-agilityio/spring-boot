package com.agility.authorizationserver.configs;

import com.agility.authorizationserver.models.Role;
import com.agility.authorizationserver.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class TestConfig {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User user = User.builder()
            .username("user")
            .password(passwordEncoder.encode("password"))
            .roles(Arrays.asList(Role.USER))
            .build();

        User manager = User.builder()
            .username("manager")
            .password(passwordEncoder.encode("password"))
            .roles(Arrays.asList(Role.USER, Role.MANAGER))
            .build();

        User admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("password"))
            .roles(Arrays.asList(Role.USER, Role.MANAGER, Role.ADMIN))
            .build();

        return new InMemoryUserDetailsManager(user, manager, admin);
    }
}
