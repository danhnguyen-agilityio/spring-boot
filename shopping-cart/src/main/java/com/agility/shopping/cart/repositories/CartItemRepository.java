package com.agility.shopping.cart.repositories;

import com.agility.shopping.cart.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * This interface implement CRUD for cart item table
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Find one cart item by shopping cart id and product id
     *
     * @param shoppingCartId Shopping cart id
     * @param productId Product id
     * @return Cart item
     */
    @Query("select s from ShoppingCart s where s.id = :shoppingCartId and s.product.id = :productId")
    CartItem findOne(@Param("shoppingCartId") long shoppingCartId, @Param("productId") long productId);
}
