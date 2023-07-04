//package com.main.miniproject.order.entity.repository;
//
//import com.main.miniproject.order.entity.Orders;
//import com.main.miniproject.order.entity.QOrders;
//import com.main.miniproject.order.entity.dto.OrderSearchDto;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.core.types.dsl.Wildcard;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.thymeleaf.util.StringUtils;
//
//import javax.persistence.EntityManager;
//import java.time.LocalDateTime;
//import java.util.List;
//
//
//public class AdminOrderRepositoryCustomImpl implements AdminOrderRepositoryCustom{
//
//   private JPAQueryFactory queryFactory;
//
//   public AdminOrderRepositoryCustomImpl(EntityManager em) {
//       queryFactory = new JPAQueryFactory(em);
//   }
//
//
//    @Override
//    public Page<Orders> getAdminOrdersPage(OrderSearchDto orderSearchDto, Pageable pageable) {
//
//        List<Orders> content = queryFactory
//                .selectFrom(QOrders.orders)
//                .where(orderDate(orderSearchDto.getSearchDate()),
//                        searchByLike(orderSearchDto.getSearchBy(), orderSearchDto.getSearchQuery()))
//                .orderBy(QOrders.orders.orderDate.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        long total = queryFactory.select(Wildcard.count).from(QOrders.orders)
//                .where(orderDate(orderSearchDto.getSearchDate()),
//                        searchByLike(orderSearchDto.getSearchBy(), orderSearchDto.getSearchQuery()))
//                .fetch();
//
//        return new PageImpl<>(content, pageable, total);
//    }
//
//
//    //주문날짜로 검색
//    private BooleanExpression orderDate(String searchDate){
//
//        LocalDateTime dateTime = LocalDateTime.now();
//
//        if(StringUtils.equals("all", searchDate) || searchDate == null){
//            return null;
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
//
//    }
//
//
//    //이름으로 검색, 주문id로 검색, 주소로 검색, 주문전화번호로 검색
//    private BooleanExpression searchByLike(String searchBy, String searchQuery){
//
//       if(StringUtils.equals("user", searchBy)){
//           return QOrders.orders.user.username.like("%" + searchQuery + "%");
//       } else if(StringUtils.equals("id", searchBy)){
//           return QOrders.orders.id.like("%" + searchQuery + "%");
//       } else if(StringUtils.equals("orderAddress", searchBy)){
//           return QOrders.orders.orderAddress.like("%" + searchQuery + "%");
//       } else if(StringUtils.equals("orderTel", searchBy)){
//           return QOrders.orders.orderTel.like("%" + searchQuery + "%");
//       }
//
//        return null;
//    }
//
//
//
//
//
//}
