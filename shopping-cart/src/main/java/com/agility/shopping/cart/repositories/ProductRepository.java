package com.agility.shopping.cart.repositories;

import com.agility.shopping.cart.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RoleRepository interface implement CRUD for product table
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Get product by given name
     *
     * @param name name of product
     * @return product with given name
     */
    Product findByName(String name);

    /**
     * Check whether or not name product exists in database
     * @param name name of product need check
     * @return true name product exists, other return false
     */
    boolean existsByName(String name);

}
