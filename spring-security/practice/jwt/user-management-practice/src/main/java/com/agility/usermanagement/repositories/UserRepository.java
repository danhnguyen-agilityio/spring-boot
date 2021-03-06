package com.agility.usermanagement.repositories;

import com.agility.usermanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRepository interface implement CRUD for user table
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Get User by username
     *
     * @param username username of user
     * @return Optional wrap User object with given username
     */
    Optional<User> findByUsername(String username);
}
