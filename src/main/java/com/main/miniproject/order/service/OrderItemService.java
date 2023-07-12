package com.main.miniproject.order.service;

import com.main.miniproject.order.entity.OrderItem;

import com.main.miniproject.order.orderItemRepository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.orderItemRepository.OrderItemRepository;
import com.main.miniproject.order.repository.MypageOrdersRepository;
import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.ProductImage;
import com.main.miniproject.product.repository.ProductImageRepository;
import com.main.miniproject.product.repository.ProductRepository;
import com.main.miniproject.user.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class OrderItemService {

//    @Autowired
//    public OrderItemRepository orderItemRepository;

    //orderItem 상세 정보 조회
    public OrderItem getOrderItemDetail(Long id) {

        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(id);

        if (orderItemOptional.isPresent()) {
            OrderItem orderItem = orderItemOptional.get();
            return orderItem;
        } else {
            return new OrderItem();
        }

    }

    private final OrderItemRepository orderItemRepository;
    private final MypageOrdersRepository mypageOrdersRepository;
    private final ProductImageRepository productImageRepository;

    public List<Orders> getOrdersList(User user) {
        return mypageOrdersRepository.findAllByUser(user);
    }

    public List<OrderItem> getProductsList(Long userId) {
        return orderItemRepository.findOrderItemsByUserId(userId);
    }

    public List<ProductImage> getImage(Long userId) {
        List<OrderItem> orderItemList = orderItemRepository.findOrderItemsByUserId(userId);

        List<ProductImage> productImageList = new ArrayList<>();
        for (int i = 0; i < orderItemList.size(); i++) {
            productImageList.add(productImageRepository.findByProduct(orderItemList.get(i).getProduct()).get(0));
        }

        return productImageList;
    }


//    주문내역은 삭제할 수 없다.

}