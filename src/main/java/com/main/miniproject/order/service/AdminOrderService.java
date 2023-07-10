package com.main.miniproject.order.service;

import com.main.miniproject.order.adminOrderRepository.AdminOrderRepository;
import com.main.miniproject.order.dto.OrdersFormDto;
import com.main.miniproject.order.entity.Orders;

import com.main.miniproject.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminOrderService {

    private final AdminOrderRepository adminOrderRepository;


    @Autowired
    public AdminOrderService(AdminOrderRepository adminOrderRepository) {
        this.adminOrderRepository = adminOrderRepository;
    }


    //주문 목록 조회
    public Orders getOrderById(Long id) {

        return adminOrderRepository.findById(id).get();
    }


    //주문 정보 상세 조회
    public OrdersFormDto getOrdersDetail(Long id) {
        Orders orders = adminOrderRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        OrdersFormDto ordersFormDto = OrdersFormDto.of(orders);
        return ordersFormDto;
    }


    //페이징 처리 후 주문 목록 조회
    public Page<Orders> getList(int page, String keyword) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("orderDate"));
        Pageable pageable = PageRequest.of(page, 3);
        Specification<Orders> ordersSpecification = search(keyword);
        return adminOrderRepository.findAll(ordersSpecification, pageable);
    }


    //검색 기능
    private Specification<Orders> search(String keyword) {
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Orders> ordersRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true);   //중복을 제거

//                Join<Orders, User> userJoin = ordersRoot.join("user");

                return criteriaBuilder.or(
//                        criteriaBuilder.like(userJoin.get("username"), "%" + keyword + "%"),
                        criteriaBuilder.like(ordersRoot.get("orderTel"), "%" + keyword + "%"));

            }
        };
    }



}
