package com.agility.shopping.cart.configs;

import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.models.Role;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.repositories.RoleRepository;
import com.agility.shopping.cart.repositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Listen list event in Spring
 */
@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public DataSeedingListener(UserRepository userRepository,
        RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create ROLE_ADMIN and ROLE_USER if 2 roles not exist in database
     * This method is called when Spring Context start or refresh
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // Add Roles
        if (roleRepository.findByName(RoleType.ADMIN.getName()) == null) {
            roleRepository.save(new Role(RoleType.ADMIN.getName()));
        }
        if (roleRepository.findByName(RoleType.USER.getName()) == null) {
            roleRepository.save(new Role(RoleType.USER.getName()));
        }

        // Admin account
        if (userRepository.findByEmail("admin@gmail.com") == null) {
            User admin = new User("admin@gmail.com",
                passwordEncoder.encode("123456"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(RoleType.ADMIN.getName()));
            admin.setRoles(roles);
            userRepository.save(admin);
        }

        // User account
        if (userRepository.findByEmail("user@gmail.com") == null) {
            User user = new User("user@gmail.com",
                passwordEncoder.encode("123456"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(RoleType.USER.getName()));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
