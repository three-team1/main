package com.main.miniproject.user.controller;

import com.main.miniproject.board.repository.BoardImageRepository;
import com.main.miniproject.board.repository.BoardRepository;
import com.main.miniproject.cart.repository.CartRepository;
import com.main.miniproject.comment.repository.CommentRepository;
import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.orderItemRepository.OrderItemRepository;
import com.main.miniproject.order.ordersRepository.OrdersRepository;
import com.main.miniproject.qna.repository.QnaRepository;
import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.repository.ReviewImageRepository;
import com.main.miniproject.review.repository.ReviewRepository;
import com.main.miniproject.user.dto.MyInfoDTO;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.repository.UserRepository;
import com.main.miniproject.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mypage")
public class MypageRestController {
    private UserInfoService userInfoService;

    private UserRepository userRepository;

    private BoardRepository boardRepository;

    private CommentRepository commentRepository;

    private QnaRepository qnaRepository;

    private ReviewRepository reviewRepository;

    private OrdersRepository ordersRepository;

    private OrderItemRepository orderItemRepository;

    private CartRepository cartRepository;

    @Autowired
    public MypageRestController(UserInfoService userInfoService,
                                UserRepository userRepository,
                                BoardRepository boardRepository,
                                CommentRepository commentRepository,
                                QnaRepository qnaRepository,
                                ReviewRepository reviewRepository,
                                OrdersRepository ordersRepository,
                                OrderItemRepository orderItemRepository,
                                CartRepository cartRepository) {
        this.userInfoService = userInfoService;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
        this.qnaRepository = qnaRepository;
        this.reviewRepository = reviewRepository;
        this.ordersRepository = ordersRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
    }

    //내 정보 조회
    @GetMapping("/myInfo")
    public User getMyInfo(@AuthenticationPrincipal UserDetails userDetails) throws UsernameNotFoundException {
        return userInfoService.getMyInfo(userDetails.getUsername());
    }

    //내 정보 수정
    @PutMapping("/myInfo")
    public void updateMyInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MyInfoDTO myInfoDTO, HttpSession session) throws UsernameNotFoundException {
        User curUser = userInfoService.getMyInfo(userDetails.getUsername());

        if(!curUser.getUsername().equals(myInfoDTO.getUsername())) {
            throw new IllegalArgumentException("유저 불일치");
        }

        User user = myInfoDTO.DTOToEntity();

        userInfoService.updateMyInfo(user, session);
    }

    //내 비밀번호 확인
    @PostMapping("/pwCheck")
    public boolean checkPassword(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("checkPassword") String checkPassword) {
        String username = userDetails.getUsername();

        return userInfoService.checkPassword(username, checkPassword);
    }

    //비밀번호 변경
    @PostMapping("/myInfo")
    @Transactional
    public boolean changePassword(@AuthenticationPrincipal UserDetails userDetails,
                                  @Valid @RequestBody MyInfoDTO myInfoDTO) {
        String username = userDetails.getUsername();

        return userInfoService.changePassword(username, myInfoDTO.getPassword(), myInfoDTO.getNewPassword(), myInfoDTO.getConfirmPassword());
    }

    //일반 회원 탈퇴
    @PostMapping("/withdraw")
    @Transactional
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestParam("checkPassword") String checkPassword) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        boolean checkResult = userInfoService.checkPassword(username, checkPassword);

        if(checkResult) {
            List<Orders> userOrders = ordersRepository.findByUser(user);

            for (Orders order : userOrders) {
                List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
                for(OrderItem orderItem : orderItems) {
                    reviewRepository.deleteByOrderItem(orderItem);
                }
                orderItemRepository.deleteByOrder(order);
            }

            boardRepository.deleteByUser(user);
            commentRepository.deleteByUser(user);
            qnaRepository.deleteByUser(user);
            reviewRepository.deleteByUser(user);
            ordersRepository.deleteByUser(user);
            cartRepository.deleteByUser(user);

            userRepository.delete(user);
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
        }
    }

    //소셜 회원 탈퇴
    @PostMapping("/withdrawSocial")
    @Transactional
    public ResponseEntity<?> deleteSocialUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        List<Orders> userOrders = ordersRepository.findByUser(user);

        for (Orders order : userOrders) {
            List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
            for(OrderItem orderItem : orderItems) {
                reviewRepository.deleteByOrderItem(orderItem);
            }
            orderItemRepository.deleteByOrder(order);
        }

        boardRepository.deleteByUser(user);
        commentRepository.deleteByUser(user);
        qnaRepository.deleteByUser(user);
        reviewRepository.deleteByUser(user);
        ordersRepository.deleteByUser(user);
        cartRepository.deleteByUser(user);

        userRepository.delete(user);
        return ResponseEntity.ok(Collections.singletonMap("success", true));
    }

}
