package com.main.miniproject.payment.controller;

import com.main.miniproject.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment")
    public String payment() {
        return "/order/KCP";
    }
}
