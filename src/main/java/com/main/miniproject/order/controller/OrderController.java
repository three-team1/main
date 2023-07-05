package com.main.miniproject.order.controller;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.cart.repository.CartRepository;
import com.main.miniproject.cart.service.CartService;
import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.service.OrderService;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.repository.UserRepository;
import com.main.miniproject.user.service.UserDetail;
import com.main.miniproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class OrderController {

	private final OrderService orderService;

	private final UserRepository userRepository;

	private final CartRepository cartRepository;

	@PostMapping("/createOrder")
	public String createOrder(Model model, @AuthenticationPrincipal UserDetail userDetail) {
		User user = userDetail.getUser();

		List<Cart> selectedCarts = cartRepository.findAllByUser(user);

		List<Orders> ordersList = new ArrayList<>();

		for (Cart cart : selectedCarts) {
			Orders order = new Orders();
			order.setCart(cart);
			order.setUser(user);
			// 주문 정보 설정 및 필요한 값들 추가
			// ...
			ordersList.add(order);
		}

		// ordersList를 데이터베이스에 저장
		// ...

		model.addAttribute("ordersList", ordersList);

		return "/order/order";
	}
}
