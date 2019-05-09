package com.agility.usermanagement;

import com.agility.usermanagement.models.User;
import com.agility.usermanagement.repositories.UserRepository;
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
        User user = User.builder()
            .username("David")
            .lastName("nguyen")
            .build();
//        userRepository.save(user);
    }
}
