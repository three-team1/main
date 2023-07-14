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
    public Page<Orders> getList(int page, String keyword, Long longKeyword, String category) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("orderDate"));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Orders> ordersSpecification = search(keyword, longKeyword, category);
        return adminOrderRepository.findAll(ordersSpecification, pageable);

    }


    //검색 기능
    private Specification<Orders> search(String keyword, Long longKeyword, String category) {
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Orders> ordersRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true);   //중복을 제거

                //id는 Long타입, orderTel은 String 타입. 각각 검색 기능 구현
                List<Predicate> predicates = new ArrayList<>();
//                List<Predicate> stringPredicate = new ArrayList<>();

                if(!StringUtils.isEmpty(keyword)){
                    switch (category){
                        case "id":
                            predicates.add(criteriaBuilder.equal(ordersRoot.get("id"), longKeyword));
                            break;
                        case "orderTel":
                            predicates.add(criteriaBuilder.like(ordersRoot.get("orderTel"), "%" + keyword + "%"));
                            break;
                        default:
                            predicates.add(criteriaBuilder.or(
                                    criteriaBuilder.equal(ordersRoot.get("id"), longKeyword),
                                    criteriaBuilder.like(ordersRoot.get("orderTel"), "%" + keyword + "%")
                            ));
                            break;
                    }
                }else{
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.equal(ordersRoot.get("id"), longKeyword),
                            criteriaBuilder.like(ordersRoot.get("orderTel"), "%" + keyword + "%")
                    ));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

            }
        };
    }






}
