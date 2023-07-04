package com.main.miniproject.order.repository;

import com.main.miniproject.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MypageOrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByUser(Long user);

}
