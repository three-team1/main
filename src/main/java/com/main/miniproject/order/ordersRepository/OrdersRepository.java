package com.main.miniproject.order.ordersRepository;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.user.entity.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.miniproject.order.entity.Orders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrdersRepository extends JpaRepository<Orders, Long>{

    Orders findByCart(Cart cart); // 단일 결과만 반환하도록 변경

    List<Orders> findAllByCart(Cart cart);
}
