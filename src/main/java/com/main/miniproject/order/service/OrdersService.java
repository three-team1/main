package com.main.miniproject.order.service;

import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.repository.OrdersRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdersService {
    private OrdersRepository ordersRepository;

//    public List<Orders> getOrdersList(Long user) {
//
//        return ordersRepository.findAllByUser(user);
//    }



//    주문내역은 삭제할 수 없다.
}
