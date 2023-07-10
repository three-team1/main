package com.main.miniproject.review.service;

import com.main.miniproject.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    Review insertReview(Review review);

    Page<Review> getAllReviews(Pageable pageable);
}
