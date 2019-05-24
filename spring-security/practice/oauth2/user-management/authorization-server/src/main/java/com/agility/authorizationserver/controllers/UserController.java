package com.agility.authorizationserver.controllers;

import com.agility.authorizationserver.exceptions.ResourceNotFoundException;
import com.agility.authorizationserver.models.User;
import com.agility.authorizationserver.repository.UserRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.agility.authorizationserver.exceptions.CustomError.USER_NOT_FOUND;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }
        userRepository.delete(user);
    }
}
