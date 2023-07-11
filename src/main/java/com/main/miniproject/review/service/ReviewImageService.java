package com.main.miniproject.review.service;

import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;

import java.util.List;

public interface ReviewImageService {

    List<ReviewImage> reviewImageList(Review review);

    void saveReviewImage(ReviewImage reviewImage);
}