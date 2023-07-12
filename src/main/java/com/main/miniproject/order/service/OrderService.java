package com.main.miniproject.order.service;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.user.entity.User;
import org.springframework.stereotype.Service;

import com.main.miniproject.order.ordersRepository.OrdersRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;




}

