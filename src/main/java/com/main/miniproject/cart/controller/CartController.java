package com.main.miniproject.cart.controller;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;



@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart")
    public String cart() {
        return "/cart/cart";
    }
}

