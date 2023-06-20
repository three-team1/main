package com.main.miniproject.review.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.main.miniproject.product.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewImage {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "review_img_id")
		private Long id;
	
		@ManyToOne
		@JoinColumn(name = "review_id")      // product 참조
		private Review review;
		
		@Column(name = "review_img_name")
		private String name;
		
		@Column(name = "review_img_url")
		private String url;
		
		@Column(name = "review_img_ori_name")
		private String originName;
	
}
