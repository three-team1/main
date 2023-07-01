package com.main.miniproject.order.repository;

import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllBy(User user);

}
