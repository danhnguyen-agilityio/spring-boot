package com.agility.shopping.cart.repositories;

import com.agility.shopping.cart.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RoleRepository class implement CRUD for product table
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Get product by given name
     */
    Product findByName(String name);
}
