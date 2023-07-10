package com.main.miniproject.order.controller;

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


@Controller
@RequiredArgsConstructor
@RequestMapping
public class OrderController {

	private final OrderService orderService;

	private final UserRepository userRepository;

	private final CartRepository cartRepository;

	private final OrderItemRepository orderItemRepository;

	@GetMapping("/viewOrderItem")
	public String viewOrderPage(@RequestParam("cartList") List<Long> cartIds, Model model) {
		List<Cart> ordersList = new ArrayList<Cart>();

		for (Long id : cartIds) {
			Cart cart = cartRepository.findById(id).get();
			ordersList.add(cart);
		}
		model.addAttribute("cartList", ordersList);

		return "order/order";
	}

}
