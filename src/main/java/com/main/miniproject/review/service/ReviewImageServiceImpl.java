package com.main.miniproject.review.service;

import com.main.miniproject.review.repository.ReviewImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReviewImageServiceImpl implements ReviewImageService {
    private ReviewImageRepository reviewImageRepository;

    @Autowired
    public ReviewImageServiceImpl(ReviewImageRepository reviewImageRepository) {
        this.reviewImageRepository = reviewImageRepository;
    }
}
