package com.main.miniproject.cart.entity;

import javax.persistence.*;

import com.main.miniproject.product.entity.Product;
import com.main.miniproject.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name ="product_image")
	private String productImage;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "cart_quantity") //상품 수량
	private Integer cartQuantity;
	

}
