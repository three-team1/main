package com.main.miniproject.cart.controller;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.cart.service.CartService;
import com.main.miniproject.qna.service.QnaService;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;



@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    private final UserRepository userRepository;

    @GetMapping("/cart")
    public String cart(Model model, @AuthenticationPrincipal UserDetails userDetails) {

            User user = userRepository.findByUsername(userDetails.getUsername()).get();

            List<Cart> cartList = cartService.getList(user);
            model.addAttribute("cartList", cartList);

        return "/cart/cart";
    }

    @GetMapping("/deleteCart/{id}")
    public String delCart(@PathVariable Long id) {

        cartService.delCart(id);


        return "redirect:/cart";
    }


}

