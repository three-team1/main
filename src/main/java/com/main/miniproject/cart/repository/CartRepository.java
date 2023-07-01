package com.main.miniproject.cart.repository;

import java.util.List;
import java.util.Optional;

import com.main.miniproject.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.user.entity.User;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser(User user);

    Cart findByProductAndUser(Product product, User user);
    
}
