package com.main.miniproject.order.service;

import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.repository.MypageOrdersRepository;
import com.main.miniproject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class OrdersService {
    private final MypageOrdersRepository ordersRepository;

//    public List<Orders> getOrdersList(User user) {
//
//        return ordersRepository.findAllByUser(user);
//    }

    public List<Orders> getOrdersByUserId(Long userId) {
        return ordersRepository.findByUserId(userId);
    }



//    주문내역은 삭제할 수 없다.
}
