package com.main.miniproject.order.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.main.miniproject.order.dto.OrderSearchDto;
import com.main.miniproject.order.dto.OrdersFormDto;
import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.service.AdminOrderService;
import com.main.miniproject.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminOrderController {
    @Autowired
    public OrderService orderService;
    
    @Autowired
    private final AdminOrderService adminOrderService;
    
    @PostMapping("/order")
    public String createOrder() {
        return "/order/order";
    }
    
    //주문정보 탭에서 주문 목록 가져오기
    @GetMapping({"/admin/orders", "/admin/orders/{page}"})
    public String ordersList(OrderSearchDto orderSearchDto, Model model,
                             @PathVariable("page")Optional<Integer> page) {
        //시작페이지는 페이지가 있으면 get()한 페이지 들고 오고 아니면 0으로 하겠다. 한 페이지에 상품은 5개씩
        Pageable pageable = PageRequest.of(page.isPresent()? page.get(): 0, 5);
        Page<Orders> orders = adminOrderService.getAllOrders(pageable);

//        if(orderSearchDto != null) {
//            orders = adminOrderService.searchOrders(orderSearchDto, pageable);
//        }else {
//            orders = adminOrderService.getAllOrders(pageable);
//        }

        model.addAttribute("orders", orders);
        model.addAttribute("ordersDto", orderSearchDto);
        model.addAttribute("maxPage", 5);
        model.addAttribute("totalPages", orders.getTotalPages());

        return "order/orderList";

    }


    //주문 정보 상세 조회
    @GetMapping("/admin/orderDetail/{orderid}")
    public String orderDetail(@PathVariable("orderid") Long orderId, Model model){

        OrdersFormDto ordersFormDto = adminOrderService.getOrdersDetail(orderId);
        model.addAttribute("OrdersFormDto", ordersFormDto);
        return "order/orderList";
    }




}

