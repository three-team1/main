package com.main.miniproject.order.repository;

import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MypageOrdersRepository extends JpaRepository<Orders, Long> {

    @Query("select o from Orders o join fetch OrderItem oi join fetch oi.product where o.user.id = :userId")
    List<Orders> findWithProductByUserId(@Param("userId") Long userId);

//    List<Orders> findAllByUser(User user);

//    List<OrderItem> findAllById(Orders orders);

//    List<Orders> findByUserId(Long userId);

    List<Orders> findAllByUser(User user);
}
