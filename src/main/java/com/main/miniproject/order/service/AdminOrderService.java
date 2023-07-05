package com.main.miniproject.order.service;

import com.main.miniproject.order.adminOrderRepository.AdminOrderRepository;
import com.main.miniproject.order.dto.OrderSearchDto;
import com.main.miniproject.order.dto.OrdersFormDto;
import com.main.miniproject.order.entity.Orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
    public Page<Orders> getAllOrders(Pageable pageable) {

        return adminOrderRepository.findAll(pageable);
    }



    //게시글 검색에 따른 게시글 출력
//    public Page<Orders> searchOrders(OrderSearchDto orderSearchDto, Pageable pageable){
//
//        String searchBy = orderSearchDto.getSearchBy();
//        String keyword = orderSearchDto.getKeyword();
//
//        if(searchBy != null && !searchBy.isEmpty()){
//            return adminOrderRepository.findByUserUsernameOrOrderTelContaining(orderSearchDto, pageable);
//        }
//
//        if(keyword != null && !keyword.isEmpty()){
//            return adminOrderRepository.findByUserUsernameOrOrderTelContaining(orderSearchDto, pageable);
//        }
//
//            return adminOrderRepository.findAll(pageable);
//    }




    //검색 기능(날짜 검색)
//    public Page<Orders> findBySearchDate(OrderSearchDto orderSearchDto, Pageable pageable){
//        LocalDateTime searchDate = orderSearchDto.getSearchDate();
//
//        LocalDateTime dateTime = LocalDateTime.now();
//
//        if(StringUtils.equals("all", searchDate) || searchDate == null){
//            return adminOrderRepository.findAll(pageable);
//        } else if(StringUtils.equals("1d", searchDate)){
//            dateTime = dateTime.minusDays(1);
//        } else if(StringUtils.equals("1w", searchDate)){
//            dateTime = dateTime.minusWeeks(1);
//        } else if(StringUtils.equals("1m", searchDate)){
//            dateTime = dateTime.minusMonths(1);
//        } else if(StringUtils.equals("6m", searchDate)){
//            dateTime = dateTime.minusMonths(6);
//        }
//
//        return adminOrderRepository.findBySearchDate(dateTime, pageable);
//
//    }

}
