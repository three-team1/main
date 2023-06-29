package com.main.miniproject.cart.repository;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUser(User user);
}
