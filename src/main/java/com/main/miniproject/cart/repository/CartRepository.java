package com.main.miniproject.cart.repository;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser(User user);
}
