package com.main.miniproject.product.entity;

import javax.persistence.*;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")      // product 참조
	private Product product;
	
	@Column(name = "product_img_name")
	private String name;
	
	@Column(name = "product_img_url")
	private String url;
	
	@Column(name = "product_img_ori_name")
	private String originName;

	public void updateProductImage(String originName, String name, String url) {
		this.originName = originName;
		this.name = name;
		this.url = url;
	}
}
