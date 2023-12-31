package com.main.miniproject.order.entity;

import javax.persistence.*;

import com.main.miniproject.product.entity.Product;

import com.main.miniproject.product.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_item")
@Builder
@Data
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders order;
	
	@Column(name = "order_quantity")
	private int orderQuantity;

	@Column(name = "reviewed")			//리뷰 작성 유무
	private boolean reviewed = false;

}
