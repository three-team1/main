package com.main.miniproject.review.service.impl;

import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.repository.ReviewImageRepository;
import com.main.miniproject.review.repository.ReviewRepository;
import com.main.miniproject.review.service.ReviewService;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    public ReviewRepository reviewRepository;

    public ReviewImageRepository reviewImageRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewImageRepository reviewImageRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewImageRepository = reviewImageRepository;
    }

    @Transactional
    @Override
    public Review insertReview(Review review) {
        User user = getCurrentUser();

        review.setUser(user);

        return reviewRepository.save(review);
    }

    public User getCurrentUser() {											// 사용자 인증정보 반환

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return userDetail.getUser();
    }
}
