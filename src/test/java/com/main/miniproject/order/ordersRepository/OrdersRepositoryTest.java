package com.main.miniproject.order.ordersRepository;

import com.main.miniproject.order.entity.Orders;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public
class OrdersRepositoryTest {
    @Autowired
    public OrdersRepository ordersRepository;

    @Test
    public void orderSave() {
        for (int i = 0; i < 50; i++) {
            Orders orders = new Orders();
            orders.setOrderTotalPrice(i*1000);
            orders.setOrderPostCode("1000" + (i*10));
            orders.setOrderAddress("비트캠프");
            orders.setOrderDetailAddress("강남구");
            ordersRepository.save(orders);
        }


    }

}