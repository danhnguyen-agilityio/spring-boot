package com.agility.shopping.cart.repositories;

import com.agility.shopping.cart.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    /**
     * Find all shopping cart by given username
     *
     * @param username username of user that own shopping cart
     * @return List shopping cart
     */
    // FIXME:: Consider user principle.username
    @Query("SELECT s FROM ShoppingCart s WHERE s.user.username = :username ")
    List<ShoppingCart> findAllByUsername(@Param("username") String username);
}
