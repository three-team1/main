package com.main.miniproject.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
	
	
	private Long productId;
	
    private String productTitle;
    
    private Double productPrice;
    
    private String productContent;
    
    private int productQuantity;
    
    private String productType;
    
    private String productSellStatus;  // ProductSellStatus를 String으로 변환해주어야 합니다.

}
