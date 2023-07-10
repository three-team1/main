package com.main.miniproject.order.controller;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.cart.repository.CartRepository;
import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.orderItemRepository.OrderItemRepository;
import com.main.miniproject.order.service.OrderService;
import com.main.miniproject.user.repository.UserRepository;
import com.main.miniproject.user.service.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class OrderController {

	private final OrderService orderService;

	private final UserRepository userRepository;

	private final CartRepository cartRepository;

	private final OrderItemRepository orderItemRepository;

	@GetMapping("/viewOrderItem")
	public String viewOrderPage(@RequestParam(value = "cartList", required = false) List<Long> cartIds, Model model) {
		List<Cart> ordersList = new ArrayList<Cart>();

		if (cartIds == null || cartIds.isEmpty()) {
			// 'cartList' 파라미터가 없을 때 처리할 로직 구현

			return "redirect:/";
		}

		for (Long id : cartIds) {
			Cart cart = cartRepository.findById(id).get();
			ordersList.add(cart);
		}
		model.addAttribute("cart", ordersList);

		return "order/order";
	}

	@PostMapping("/insertOrders")
	public String insertOrders(Orders orders){

		return "";
	}


}
