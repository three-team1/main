package com.main.miniproject.review.dto;

import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewImageDTO {
    private Long id;
    private Long reviewId; // Review 참조
    private String name;
    private String url;
    private String originName;

    public ReviewImage DTOToEntity() {
        ReviewImage reviewImage = ReviewImage.builder()
                .id(this.id)
                .review(Review.builder().id(this.reviewId).build())
                .name(this.name)
                .url(this.url)
                .originName(this.originName)
                .build();

        return reviewImage;
    }
}