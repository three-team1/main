package com.main.miniproject.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

	
	@GetMapping("/order")
	public String getOrder() {
		
		return "/order/order";
	}
}
