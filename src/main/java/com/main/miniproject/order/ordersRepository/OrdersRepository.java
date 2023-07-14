package com.main.miniproject.order.ordersRepository;


import com.main.miniproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.main.miniproject.order.entity.Orders;

import java.util.List;


@Repository
@Transactional
public interface OrdersRepository extends JpaRepository<Orders, Long>{

    List<Orders> findByUser(User user);

    void deleteByUser(User user);

}
