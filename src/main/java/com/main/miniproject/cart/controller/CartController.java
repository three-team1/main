package com.main.miniproject.cart.controller;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.cart.service.CartService;
import com.main.miniproject.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart")
    public String cart(Model model) {

        List<Cart> cartList = cartService.getList();
        model.addAttribute("cartList", cartList);

        return "/cart/cart";
    }
}

