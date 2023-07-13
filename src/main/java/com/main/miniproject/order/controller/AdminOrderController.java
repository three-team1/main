package com.main.miniproject.order.controller;

import java.util.List;
import java.util.Optional;

import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.order.orderItemRepository.OrderItemRepository;
import com.main.miniproject.order.ordersRepository.OrdersRepository;
import com.main.miniproject.order.service.OrderItemService;
import com.main.miniproject.product.dto.ProductFormDto;
import com.main.miniproject.product.entity.ProductImage;
import com.main.miniproject.product.repository.ProductImageRepository;
import com.main.miniproject.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedFormContentFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.main.miniproject.order.dto.OrdersFormDto;
import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.service.AdminOrderService;
import com.main.miniproject.order.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminOrderController {
    @Autowired
    public OrderService orderService;
    
    @Autowired
    private AdminOrderService adminOrderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @PostMapping("/order")
    public String createOrder() {
        return "/order/order";
    }


    //주문관리 탭에서 목록 가져오기( +페이징 기능, 검색 기능 )
    @GetMapping("/admin/orders")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "keyword", defaultValue = "")String keyword, Long longKeyword,
                       @RequestParam(value = "category", defaultValue = "") String category) {

        List<Orders> orders = ordersRepository.findAll();

        Page<Orders> ordersPage = adminOrderService.getList(page, keyword, longKeyword, category);

        model.addAttribute("ordersPage", ordersPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("orders", orders);


        return "order/orderList";
    }


    //주문 정보 상세 조회(orderItem)
    @GetMapping("/admin/orderDetail/{orderId}")
    public String orderDetail(@PathVariable("orderId") Long orderId, Model model){

        List<OrderItem> orderItemList = orderItemRepository.findOrderItemsByOrderId(orderId);

        List<ProductImage> productImageList = productImageRepository.findProductImagesByProductId(orderItemList.get(0).getProduct().getId());

        model.addAttribute("orderItemList", orderItemList);
        model.addAttribute("productImageList", productImageList);

        return "order/orderForm";
    }



}

