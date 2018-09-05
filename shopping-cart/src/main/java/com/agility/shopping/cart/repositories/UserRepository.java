package com.agility.shopping.cart.repositories;

import com.agility.shopping.cart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository class implement CRUD for user table
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Get User by email
     * @param email email of user
     * @return User object with given email
     */
    User findByEmail(String email);
}
