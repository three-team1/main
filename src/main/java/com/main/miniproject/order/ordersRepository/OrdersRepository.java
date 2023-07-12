package com.main.miniproject.order.ordersRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.main.miniproject.order.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long>{
	
	

}
