package com.agility.shopping.cart.repositories;

import com.agility.shopping.cart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

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
