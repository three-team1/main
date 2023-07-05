package com.main.miniproject.product.dto;

import com.main.miniproject.product.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSellingProductDTO {
		
	private Product product;
	
	private int salesCount;
		
	private String productName;
}
