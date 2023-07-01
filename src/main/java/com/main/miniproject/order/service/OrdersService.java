//package com.main.miniproject.order.service;
//
//import com.main.miniproject.order.entity.OrderItem;
//import com.main.miniproject.order.entity.Orders;
//import com.main.miniproject.order.repository.OrdersRepository;
//import com.main.miniproject.user.entity.User;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class OrdersService {
//    private OrdersRepository ordersRepository;
//
//    public List<Orders> getOrdersList(User user) {
//
//        return ordersRepository.findAllBy(user);
//    }
//
////    주문내역은 삭제할 수 없다.
//    public List<OrderItem> getOrderItemList(User user) {
//        return ordersRepository.findOrderItemBy(user);
//    }
//
//}
