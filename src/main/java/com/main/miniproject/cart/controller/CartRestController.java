package com.main.miniproject.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.cart.repository.CartRepository;
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
	private CartRepository cartRepository;
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/insertCart/{id}")
	public ResponseEntity<?> insertCart(@PathVariable Long id, @AuthenticationPrincipal UserDetail userDetail) {

	    User user = userRepository.findById(userDetail.getUser().getId()).get();

	    Product product = productRepository.findById(id).get();

		if(cartRepository.findByProductAndUser(product, user) != null){
			return ResponseEntity.ok().body("existed");
		}

		Cart cart = Cart.builder()
				.product(product)
				.user(user)
				.cartQuantity(1)
				.build();
		cartService.insertCart(cart);

	    return ResponseEntity.ok().build();
	}
	
    @GetMapping("/api/cart")
    public ResponseEntity<List<Cart>> getCart(@AuthenticationPrincipal UserDetail userDetail) {
    	
    	User user = userDetail.getUser();
    	
        List<Cart> cartItems = cartService.getList(user);
        return ResponseEntity.ok(cartItems);
    }
    
    @GetMapping("/api/cart/user")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal UserDetail userDetail) {
    	
    	User user = userDetail.getUser();
    	
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/api/cart/count")
    public ResponseEntity<Integer> getCartItemCount(@AuthenticationPrincipal UserDetail userDetail) {
       

        User user = userDetail.getUser();   	
        int count = cartService.countCart(user);
        
        System.out.println("상품종류개수 : " + count);
        return ResponseEntity.ok(count);
    }

}
