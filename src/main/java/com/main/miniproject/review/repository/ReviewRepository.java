package com.main.miniproject.review.repository;

import com.main.miniproject.review.entity.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByUserUsername(Pageable pageable, String username);

    Page<Review> findByReviewTitleContainingOrReviewContentContaining(String title, String content, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.user.username = :username AND (r.reviewTitle LIKE %:keyword% OR r.reviewContent LIKE %:keyword%)")
    Page<Review> findByUserUsernameAndKeyword(Pageable pageable, @Param("username") String username, @Param("keyword") String keyword);

}