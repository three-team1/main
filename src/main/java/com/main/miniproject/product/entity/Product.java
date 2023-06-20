package com.main.miniproject.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@Entity
public class Product {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="product_id")
    private Long id;
    
    @Column(name ="product_title")
    private String productTitle;

    @Column(name ="product_price")
    private Double productPrice;

    @Column(name ="product_content")
    private String productContent;
    
    @Column(name ="product_quantity")
    private int productQuantity;   // 상품 재고수량
    
	@Column
	@Enumerated(EnumType.STRING)
    private ProductSellStatus productSellStatus;
    
}
