package com.main.miniproject.order.service;

import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.orderItemRepository.OrderItemRepository;
import com.main.miniproject.order.repository.MypageOrdersRepository;
import com.main.miniproject.user.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final MypageOrdersRepository mypageOrdersRepository;

    public List<Orders> getOrdersList(User user) {
        return mypageOrdersRepository.findAllByUser(user);
    }

    public List<OrderItem> getProductsList(Long userId) {
        return orderItemRepository.findOrderItemsByUserId(userId);
    }




//    주문내역은 삭제할 수 없다.
}
