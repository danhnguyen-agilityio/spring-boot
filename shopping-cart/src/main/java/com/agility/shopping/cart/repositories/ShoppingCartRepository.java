package com.agility.shopping.cart.repositories;

import com.agility.shopping.cart.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ShoppingCartRepository interface implement CRUD for shopping cart table
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    /**
     * Check whether or not product name exists in database
     *
     * @param name Product name need check
     * @return true if name product exists, other return false
     */
    boolean existsByName(String name);
}
