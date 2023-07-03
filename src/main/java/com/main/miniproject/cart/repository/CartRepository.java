package com.main.miniproject.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.product.entity.Product;
import com.main.miniproject.user.entity.User;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser(User user);

    Cart findByProductAndUser(Product product, User user);
    
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user = :user")
    void clearCartByUser(@Param("user") User user);    
    
    @Query("SELECT COUNT(DISTINCT c.product) FROM Cart c WHERE c.user = :user")
    int countDistinctProductsByUser(@Param("user") User user);
    
    
}
