package com.main.miniproject.order.adminOrderRepository;

import com.main.miniproject.order.entity.Orders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AdminOrderRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findById(Long id);

    Page<Orders> findAll(Specification<Orders> ordersSpecification, Pageable pageable);

}
