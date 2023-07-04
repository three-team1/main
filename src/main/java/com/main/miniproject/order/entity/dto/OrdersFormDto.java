package com.main.miniproject.order.entity.dto;

import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.payment.entity.Payment;
import com.main.miniproject.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class OrdersFormDto {

    private Long id;

    private LocalDateTime orderDate = LocalDateTime.now();

    private int orderTotalPrice;

    private String orderPostCode;

    private String orderAddress;

    private String orderDetailAddress;

    private String orderTel;

    private User user;

    private Payment payment;

    private static ModelMapper modelMapper = new ModelMapper();

    public Orders createOrders() {
        return modelMapper.map(this, Orders.class);
    }

    public static OrdersFormDto of(Orders orders){
        return modelMapper.map(orders, OrdersFormDto.class);
    }

}
