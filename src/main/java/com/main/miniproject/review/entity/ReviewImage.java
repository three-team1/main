package com.main.miniproject.review.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.main.miniproject.review.dto.ReviewImageDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	
	public ReviewImageDTO EntityToDTO() {
		ReviewImageDTO reviewImageDTO = ReviewImageDTO.builder()
				.id(this.id)
				.reviewId(this.review.getId()) // Review 참조
				.name(this.name)
				.url(this.url)
				.originName(this.originName)
				.build();
		return reviewImageDTO;
	}
}