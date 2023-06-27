package com.main.miniproject.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "product_image")
@Entity
public class ProductImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_img_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")      // product 참조
	private Product product;
	
	@Column(name = "product_img_name")
	private String name;
	
	@Column(name = "product_img_url")
	private String url;
	
	@Column(name = "product_img_ori_name")
	private String originName;
	
}
