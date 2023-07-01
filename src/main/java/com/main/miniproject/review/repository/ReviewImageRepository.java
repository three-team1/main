package com.main.miniproject.review.repository;

import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

    List<ReviewImage> findByReview(Review review);

}
