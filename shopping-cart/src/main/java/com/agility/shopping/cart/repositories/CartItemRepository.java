package com.agility.shopping.cart.repositories;

import com.agility.shopping.cart.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface implement CRUD for cart item table
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
