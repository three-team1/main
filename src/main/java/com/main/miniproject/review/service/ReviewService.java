package com.main.miniproject.review.service;

import com.main.miniproject.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    Review insertReview(Review review);

    Page<Review> getAllReviews(Pageable pageable);

    Page<Review> searchReview(Pageable pageable, String keyword);

    Page<Review> getMyReviews(Pageable pageable, String username);

    Page<Review> searchMyReviews(Pageable pageable, String username, String keyword);

    void deleteReview(Long id);
}
