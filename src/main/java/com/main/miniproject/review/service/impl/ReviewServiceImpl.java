package com.main.miniproject.review.service.impl;

import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;
import com.main.miniproject.review.repository.ReviewImageRepository;
import com.main.miniproject.review.repository.ReviewRepository;
import com.main.miniproject.review.service.ReviewService;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserDetail;
import com.main.miniproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ReviewRepository reviewRepository;

    public ReviewImageRepository reviewImageRepository;

    public UserService userService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewImageRepository reviewImageRepository,
                             UserService userService) {
        this.reviewRepository = reviewRepository;
        this.reviewImageRepository = reviewImageRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Review insertReview(Review review) {
        User user = getCurrentUser();

        review.setUser(user);

        return reviewRepository.save(review);
    }

    @Override
    public Page<Review> getAllReviews(Pageable pageable) {

        return reviewRepository.findAll(pageable);
    }

    @Override
    public Page<Review> searchReview(Pageable pageable, String keyword) {
        return reviewRepository.findByReviewTitleContainingOrReviewContentContaining(keyword, keyword, pageable);
    }

    @Override
    public Page<Review> getMyReviews(Pageable pageable, String username) {
        return reviewRepository.findByUserUsername(pageable, username);
    }

    @Override
    public Page<Review> searchMyReviews(Pageable pageable, String username, String keyword) {
        return reviewRepository.findByUserUsernameAndKeyword(pageable, username, keyword);
    }

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


    public User getCurrentUser() {
        // 사용자 인증정보 반환
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return userDetail.getUser();
    }
}
