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

//    @Query("select o " +
//            "from Orders o " +
//            "join fetch OrderItem oi " +
//            "join fetch oi.product " +
//            "where o.user.id = :userId")
//    List<Orders> findWithProductByUserId(@Param("userId") Long userId);

//    SELECT *
//    FROM ORDERS O
//    JOIN ORDER_ITEM OI
//    ON O.ORDER_ID = OI.ORDER_ID
//    JOIN PRODUCT P
//    ON OI.PRODUCT_ID = P.PRODUCT_ID
//    WHERE O.USER_ID = 1; 이 쿼리를 응용해보자.
    @Query(
            value = "SELECT * " +
                   " FROM ORDERS O " +
            " JOIN ORDER_ITEM OI " +
            " ON O.ORDER_ID = OI.ORDER_ID ", nativeQuery = true
    )
    List<Orders> findWithProductByUserId(@Param("userId") Long userId);


//    List<Orders> findAllByUser(User user);

//    List<OrderItem> findAllById(Orders orders);

//    List<Orders> findByUserId(Long userId);

    List<Orders> findAllByUser(User user);
}
