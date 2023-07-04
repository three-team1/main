package com.main.miniproject.order.entity.repository;

import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.entity.dto.OrderSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AdminOrderRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findById(Long id);

//    @Query(value = "SELECT * FROM orders WHERE user LIKE %:searchBy% or %:searchQuery&" , nativeQuery = true)
//    Page<Orders> findBySearchByAndSearchQuery(String searchBy, String searchQuery, Pageable pageable);

//    @Query(value = "select * from ordersearchdto where searchQuery like %:orderTel% ")
//    Page<Orders> findByUserUsername(OrderSearchDto orderSearchDto, Pageable pageable);

//   Page<Orders> findByUserUsernameOrOrderTelContaining(OrderSearchDto orderSearchDto,Pageable pageable);


}
