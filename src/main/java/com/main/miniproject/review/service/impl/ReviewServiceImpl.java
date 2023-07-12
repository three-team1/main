package com.main.miniproject.review.service.impl;

import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.order.orderItemRepository.OrderItemRepository;
import com.main.miniproject.review.dto.ReviewDTO;
import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;
import com.main.miniproject.review.repository.ReviewImageRepository;
import com.main.miniproject.review.repository.ReviewRepository;
import com.main.miniproject.review.service.ReviewService;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;

    private ReviewImageRepository reviewImageRepository;

    private UserService userService;

    private OrderItemRepository orderItemRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewImageRepository reviewImageRepository,
                             UserService userService,
                             OrderItemRepository orderItemRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewImageRepository = reviewImageRepository;
        this.userService = userService;
        this.orderItemRepository = orderItemRepository;
    }

    //리뷰 작성
    @Transactional
    @Override
    public ReviewDTO insertReview(Long userId, Long orderItemId, ReviewDTO reviewDTO) {
        //리뷰 미작성 주문 상품 찾기
        List<OrderItem> notReviewedOrderItems = orderItemRepository.findNotReviewedOrderItemsByUserId((userId));

        //해당 회원의 주문 상품인지 일치 여부 확인
        OrderItem orderItem = notReviewedOrderItems.stream()
                .filter(item -> item.getId().equals(orderItemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 주문 상품 아님"));

        Review review = reviewDTO.DTOToEntity();
        review.setOrderItem(orderItem);
        Review savedReview = reviewRepository.save(review);

        //리뷰 작성 후 OrderItems의 reviewed 변경
        orderItem.setReviewed(true);
        orderItemRepository.save(orderItem);

        ReviewDTO savedReviewDTO = ReviewDTO.builder()
                .id(savedReview.getId())
                .reviewTitle(savedReview.getReviewTitle())
                .reviewContent(savedReview.getReviewContent())
                .reviewRegdate(savedReview.getReviewRegdate())
                .reviewRating(savedReview.getReviewRating())
                .userId(savedReview.getUser().getId())
                .orderItemId(savedReview.getOrderItem().getId())
                .build();

        return savedReviewDTO;
    }

    //리뷰 전체 목록 조회
    @Override
    public Page<Review> getAllReviews(Pageable pageable) {

        return reviewRepository.findAll(pageable);
    }

    //전체 리뷰 검색
    @Override
    public Page<Review> searchReview(Pageable pageable, String keyword) {
        return reviewRepository.findByOrderItemProductProductTitleContainingOrReviewContentContaining(keyword, keyword, pageable);
    }

    //내 리뷰 목록 조회
    @Override
    public Page<Review> getMyReviews(Pageable pageable, String username) {
        return reviewRepository.findByUserUsername(pageable, username);
    }

    //내 리뷰 검색
    @Override
    public Page<Review> searchMyReviews(Pageable pageable, String username, String keyword) {
        return reviewRepository.findByUserUsernameAndKeyword(pageable, username, keyword);
    }

    //리뷰 삭제
    @Transactional
    @Override
    public void deleteReview(Long id) {
        User user = userService.getCurrentUser();
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("리뷰를 찾지 못함"));

        if(!review.getUser().getUsername().equals(user.getUsername())) {
            throw new RuntimeException("삭제 권한 없음");
        }

        //첨부 이미지 삭제
        List<ReviewImage> reviewImages = reviewImageRepository.findByReview(review);

        for (ReviewImage reviewImage : reviewImages) {
            try {
                Path filePate = Paths.get("." + reviewImage.getUrl());
                Files.deleteIfExists(filePate);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }

            reviewImageRepository.delete(reviewImage);
        }

        reviewRepository.deleteById(id);
    }

}