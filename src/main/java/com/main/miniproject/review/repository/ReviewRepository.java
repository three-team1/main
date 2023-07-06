package com.main.miniproject.review.repository;

import com.main.miniproject.review.entity.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByUserUsername(String username, Pageable pageable);

    Page<Review> findByReviewTitleContainingOrReviewContentContaining(String title, String content, Pageable pageable);

}