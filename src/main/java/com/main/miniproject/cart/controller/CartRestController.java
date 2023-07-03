package com.main.miniproject.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.cart.service.CartService;
import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.repository.ProductRepository;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.repository.UserRepository;
import com.main.miniproject.user.service.UserDetail;

@RestController
public class CartRestController {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/insertCart/{id}")
	public ResponseEntity<Void> insertCart(@PathVariable Long id, @AuthenticationPrincipal UserDetail userDetail) {

	    User user = userRepository.findById(userDetail.getUser().getId()).get(); 

	    Product product = productRepository.findById(id).get();

	    Cart cart = Cart.builder()
	            .product(product)
	            .user(user)
	            .cartQuantity(1)
	            .build();

	    cartService.insertCart(cart);
	    return ResponseEntity.ok().build();
	}

}
