package com.main.miniproject.review.service.impl;

import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;
import com.main.miniproject.review.repository.ReviewImageRepository;
import com.main.miniproject.review.service.ReviewImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReviewImageServiceImpl implements ReviewImageService {
    private ReviewImageRepository reviewImageRepository;

    @Autowired
    public ReviewImageServiceImpl(ReviewImageRepository reviewImageRepository) {
        this.reviewImageRepository = reviewImageRepository;
    }

    @Override
    public List<ReviewImage> reviewImageList(Review review) {

        return reviewImageRepository.findByReview(review);
    }

    @Transactional
    @Override
    public void saveReviewImage(ReviewImage reviewImage) {
        reviewImageRepository.save(reviewImage);
    }
}
