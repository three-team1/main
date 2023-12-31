package com.main.miniproject.review.entity;

import javax.persistence.*;

import com.main.miniproject.review.dto.ReviewImageDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReviewImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_img_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")      // product 참조
	private Review review;

	@Column(name = "review_img_name")
	private String name;

	@Column(name = "review_img_url")
	private String url;

	@Column(name = "review_img_ori_name")
	private String originName;

	@Column(name = "review_img_height")
	private Integer height;
	
	public ReviewImageDTO EntityToDTO() {
		ReviewImageDTO reviewImageDTO = ReviewImageDTO.builder()
				.id(this.id)
				.reviewId(this.review.getId()) // Review 참조
				.name(this.name)
				.url(this.url)
				.originName(this.originName)
				.height(this.height)
				.build();
		return reviewImageDTO;
	}
}