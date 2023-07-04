package com.main.miniproject.order.service;

import org.springframework.stereotype.Service;

import com.main.miniproject.order.ordersRepository.OrdersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    public OrdersRepository orderRepository;
}

