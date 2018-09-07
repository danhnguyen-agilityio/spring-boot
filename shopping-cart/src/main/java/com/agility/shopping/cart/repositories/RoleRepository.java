package com.agility.shopping.cart.repositories;

import com.agility.shopping.cart.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RoleRepository interface implement CRUD for role table
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Get Role by name
     * @param name name of role
     * @return Role object with given name
     */
    Role findByName(String name);
}
