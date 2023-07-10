package com.main.miniproject.order.controller;

<<<<<<< HEAD
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
=======
import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.cart.repository.CartRepository;


import com.main.miniproject.order.orderItemRepository.OrderItemRepository;
import com.main.miniproject.order.service.OrderService;
import com.main.miniproject.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

>>>>>>> a94bc4aec060891bb0a0be307e4e4e6a2d9a41f9

@Controller
public class OrderController {

	
	@GetMapping("/order")
	public String getOrder() {
		
		return "/order/order";
	}
<<<<<<< HEAD
=======

>>>>>>> a94bc4aec060891bb0a0be307e4e4e6a2d9a41f9
}
