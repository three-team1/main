package com.main.miniproject.cart.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.cart.service.CartService;
import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.repository.ProductRepository;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.repository.UserRepository;
import com.main.miniproject.user.service.UserDetail;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
@RequestMapping
public class CartController {

    private final CartService cartService;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    @GetMapping("/cart")
    public String cart(Model model, @AuthenticationPrincipal UserDetail userDetail) {

            //User user = userRepository.findByUsername(userDetails.getUsername()).get(); // 재민님로직
    	
            User user = userRepository.findById(userDetail.getUser().getId()).get(); // 변경사항 ()

            List<Cart> cartList = cartService.getList(user);
            model.addAttribute("cartList", cartList);

        return "/cart/cart";
    }

    
    // 해당메소드 RestController로 이동
    
//    @PostMapping("/insertCart/{id}")
//    public void insertCart(@PathVariable Long id, @AuthenticationPrincipal UserDetail userDetail) {
//
//        // User user = userRepository.findByUsername(userDetails.getUsername()).get(); // 재민님 로직
//    	
//    	User user = userRepository.findById(userDetail.getUser().getId()).get();  	// 변경사항 ()
//    	
//        Product product = productRepository.findById(id).get();
//
//        Cart cart = Cart.builder()
//                .product(product)
//                .user(user)
//                .cartQuantity(1)
//                .build();
//
//        cartService.insertCart(cart);
//
//    }

    @GetMapping("/deleteCart/{id}")
    public String delCart(@PathVariable Long id) {

        cartService.delCart(id);


        return "redirect:/cart";
    }

    @PostMapping("/updateCart")
    public String updateCart(Cart cart) {
        System.out.println("----------------------- cart :" + cart);

        Cart updateCart = cartService.getCart(cart.getId());
        updateCart.setCartQuantity(cart.getCartQuantity());
        System.out.println("----------------------- updateCart :" + updateCart);

        cartService.updateCart(updateCart);
        return "redirect:/cart";
    }


}
