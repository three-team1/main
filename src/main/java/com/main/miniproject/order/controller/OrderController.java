package com.main.miniproject.order.controller;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.repository.OrderRepository;
import com.main.miniproject.order.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    public OrderService orderService;


    @PostMapping("/order")
    public String createOrder() {
        return "/order/order";
    }
}
