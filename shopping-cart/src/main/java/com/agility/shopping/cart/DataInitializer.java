package com.agility.shopping.cart;

import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.models.Role;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.repositories.RoleRepository;
import com.agility.shopping.cart.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * This class used to initial user data when application is started
 */
@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create ROLE_ADMIN and ROLE_MEMBER if 2 roles not exist in database
     */
    @Override
    public void run(String... strings) throws Exception {
        // Add Roles
        if (roleRepository.findByName(RoleType.ADMIN.getName()) == null) {
            roleRepository.save(new Role(RoleType.ADMIN.getName()));
        }
        if (roleRepository.findByName(RoleType.MEMBER.getName()) == null) {
            roleRepository.save(new Role(RoleType.MEMBER.getName()));
        }

        // Admin account
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User("admin",
                passwordEncoder.encode("123456"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(RoleType.ADMIN.getName()));
            admin.setRoles(roles);
            userRepository.save(admin);
        }

        // User account
        if (userRepository.findByUsername("user") == null) {
            User user = new User("user",
                passwordEncoder.encode("123456"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(RoleType.MEMBER.getName()));
            user.setRoles(roles);
            userRepository.save(user);
        }

        // User1 account
        if (userRepository.findByUsername("user1") == null) {
            User user = new User("user1",
                passwordEncoder.encode("123456"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(RoleType.MEMBER.getName()));
            user.setRoles(roles);
            userRepository.save(user);
        }

        log.debug("Printing all users....");
        // TODO: Why this code run fail
        // userRepository.findAll().forEach(v -> log.debug(" User :", v.toString() ));
    }
}
