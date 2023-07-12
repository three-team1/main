package com.main.miniproject.order.orderItemRepository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.main.miniproject.order.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	
	@Query("SELECT o.product.id, COUNT(o) as salesCount " +
		       "FROM OrderItem o " +
		       "GROUP BY o.product.id " +
		       "ORDER BY salesCount DESC")
		List<Object[]> findTopSellingProducts(Pageable pageable);

//	@Query("select o " +
//			"from Orders o " +
//			"join fetch OrderItem oi " +
//			"join fetch oi.product " +
//			"where o.user.id = :userId")
//	List<OrderItem> findWithProductByUserId(@Param("userId") Long userId);
}
