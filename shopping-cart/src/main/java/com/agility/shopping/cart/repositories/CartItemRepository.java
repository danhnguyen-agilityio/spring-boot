package com.agility.shopping.cart.repositories;

import com.agility.shopping.cart.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This interface implement CRUD for cart item table
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Find one cart item by shopping cart id and product id
     *
     * @param shoppingCartId Shopping cart id
     * @param productId      Product id
     * @return Cart item
     */
    @Query("select c from CartItem c where c.shoppingCart.id = :shoppingCartId and c.product.id = :productId")
    CartItem findOne(@Param("shoppingCartId") long shoppingCartId, @Param("productId") long productId);

    /**
     * Find all cart item by shopping cart id
     *
     * @param shoppingCartId Shopping cart id
     * @return List cart item
     */
    @Query("select c from CartItem c where c.shoppingCart.id = :shoppingCartId ")
    List<CartItem> findAllByShoppingCartId(@Param("shoppingCartId") long shoppingCartId);

    /**
     * Find one cart item by cart item id and shopping cart id
     *
     * @param cartItemId     Cart item id
     * @param shoppingCartId Shopping cart id
     * @return Cart item
     */
    @Query("select c from CartItem c where c.id = :cartItemId and c.shoppingCart.id = :shoppingCartId ")
    CartItem findOneByCartItemIdAndShoppingCartId(@Param("cartItemId") long cartItemId,
                                                  @Param("shoppingCartId") long shoppingCartId);
}
