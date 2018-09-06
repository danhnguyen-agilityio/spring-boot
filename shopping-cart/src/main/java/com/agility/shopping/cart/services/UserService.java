package com.agility.shopping.cart.services;

import com.agility.shopping.cart.models.Role;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserService class manages the user stored in the database
 * and link it to the security
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(name);

        if (user == null) {
            throw new UsernameNotFoundException(name);
            // FIXME:: Return error code to client when invalid username or password
        }

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), new HashSet<>()
        );
    }

    /**
     * Get roles of user by username
     * @param username
     * @return List role of user with given name
     */
    public Set<Role> getRolesByUsername(String username) {
         User user = userRepository.findByUsername(username);

         if (user == null) {
             return new HashSet<>();
         } else {
             return user.getRoles();
         }
    }

    /**
     * Get name roles of user by username
     * @param username
     * @return List name role of user with given name
     */
    public Set<String> getNameRolesByUsername(String username) {
        return getRolesByUsername(username).stream()
            .map(role -> role.getName())
            .collect(Collectors.toSet());
    }
}
