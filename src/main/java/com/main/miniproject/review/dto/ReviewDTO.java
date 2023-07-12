package com.main.miniproject.review.dto;

import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.review.entity.Review;
import com.main.miniproject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;
    private String reviewTitle;
    private String reviewContent;
    private LocalDateTime reviewRegdate;
    private Integer reviewRating;
    private Long userId;
    private Long orderItemId;

    public ReviewDTO(String reviewContent, Integer reviewRating, Long userId, Long orderItemId) {
        this.reviewContent = reviewContent;
        this.reviewRating = reviewRating;
        this.userId = userId;
        this.orderItemId = orderItemId;
    }

    public Review DTOToEntity() {
        Review review = Review.builder()
                .id(this.id)
                .reviewTitle(this.reviewTitle)
                .reviewContent(this.reviewContent)
                .reviewRegdate(this.reviewRegdate)
                .reviewRating(this.reviewRating)
                .user(User.builder().id(this.userId).build())
                .orderItem(OrderItem.builder().id(this.orderItemId).build())
                .build();

        return review;
    }
}