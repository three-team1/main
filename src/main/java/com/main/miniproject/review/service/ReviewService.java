package com.main.miniproject.review.service;

import com.main.miniproject.review.dto.ReviewDTO;
import com.main.miniproject.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    ReviewDTO insertReview(ReviewDTO reviewDTO);

    Page<Review> getAllReviews(Pageable pageable);

    Page<Review> searchReview(Pageable pageable, String keyword);

    Page<Review> getMyReviews(Pageable pageable, String username);

    Page<Review> searchMyReviews(Pageable pageable, String username, String keyword);

    void deleteReview(Long id);
}