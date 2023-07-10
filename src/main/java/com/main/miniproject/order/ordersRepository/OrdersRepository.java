package com.main.miniproject.order.ordersRepository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.miniproject.order.entity.Orders;


public interface OrdersRepository extends JpaRepository<Orders, Long>{



	

}
