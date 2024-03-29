package com.pack.example.clientresttemplate;

import com.pack.example.clientresttemplate.user.ClientUser;
import com.pack.example.clientresttemplate.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("user").orElse(null) == null) {
            ClientUser clientUser = new ClientUser();
            clientUser.setUsername("user");
            clientUser.setPassword(passwordEncoder.encode("user"));
            userRepository.save(clientUser);
        }
    }
}
