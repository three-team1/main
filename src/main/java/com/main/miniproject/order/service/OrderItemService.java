package com.main.miniproject.order.service;

import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.order.orderItemRepository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    @Autowired
    public OrderItemRepository orderItemRepository;

    //orderItem 상세 정보 조회
    public OrderItem getOrderItemDetail(Long id) {

        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(id);

        if(orderItemOptional.isPresent()) {
            OrderItem orderItem = orderItemOptional.get();
            return orderItem;
        } else{
            return new OrderItem();
        }

    }
}
