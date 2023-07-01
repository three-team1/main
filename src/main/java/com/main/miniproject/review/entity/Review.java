package com.main.miniproject.review.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.review.dto.ReviewDTO;
import com.main.miniproject.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long id;
	
	@Column(name = "review_title")
	private String reviewTitle;
	
	@Column(name = "review_content")
	private String reviewContent;
	
	@Column(name = "review_regdate")
	private LocalDateTime reviewRegdate = LocalDateTime.now();

	@Column(name = "review_rating")
	private Integer reviewRating;		//별점 추가
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_item_id")
	private OrderItem orderItem;

	public void setUser(User user) {
		this.user = user;
	}

	public ReviewDTO EntityToDTO() {
		ReviewDTO reviewDTO = ReviewDTO.builder()
				.id(this.id)
				.reviewTitle(this.reviewTitle)
				.reviewContent(this.reviewContent)
				.reviewRegdate(this.reviewRegdate)
				.reviewRating(this.reviewRating)
				.userId(this.user.getId())
				.orderItemId(this.orderItem.getId())
				.build();
		return reviewDTO;
	}
}
