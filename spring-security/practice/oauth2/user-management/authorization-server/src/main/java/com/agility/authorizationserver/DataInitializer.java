package com.agility.authorizationserver;

import com.agility.authorizationserver.models.Role;
import com.agility.authorizationserver.models.User;
import com.agility.authorizationserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * This class used to initial user data right after application context loaded (even in unit test)
 */
@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.findByUsername("user").isPresent()) {
            User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles(Arrays.asList(Role.USER))
                .build();
            userRepository.save(user);

            User manager = User.builder()
                .username("manager")
                .password(passwordEncoder.encode("password"))
                .roles(Arrays.asList(Role.USER, Role.MANAGER))
                .build();
            userRepository.save(manager);

            User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .roles(Arrays.asList(Role.USER, Role.MANAGER, Role.ADMIN))
                .build();
            userRepository.save(admin);
        }
    }
}
