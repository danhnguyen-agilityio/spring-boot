package com.agility.shopping.cart.repositories;

import com.agility.shopping.cart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserRepository interface implement CRUD for user table
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Get User by username
     *
     * @param username username of user
     * @return User object with given username
     */
    User findByUsername(String username);
}
