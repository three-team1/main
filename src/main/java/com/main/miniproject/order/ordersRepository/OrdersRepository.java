package com.main.miniproject.order.ordersRepository;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.user.entity.User;
import org.aspectj.weaver.ast.Or;
import com.main.miniproject.order.entity.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.main.miniproject.order.entity.Orders;
import org.springframework.transaction.annotation.Transactional;



@Repository
@Transactional
public interface OrdersRepository extends JpaRepository<Orders, Long>{




}
