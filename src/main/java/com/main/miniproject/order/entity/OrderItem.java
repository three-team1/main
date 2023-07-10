package com.main.miniproject.order.entity;

import javax.persistence.*;

import com.main.miniproject.product.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD
=======
@Table(name = "order_item")
@Builder
>>>>>>> a94bc4aec060891bb0a0be307e4e4e6a2d9a41f9
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
	
	
}
