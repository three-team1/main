package com.main.miniproject.product.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.main.miniproject.product.dto.ProductFormDto;
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

    @Lob
    @Column(name ="product_content")
    private String productContent;
    
    @Column(name ="product_quantity")
    private int productQuantity;   // 상품 재고수량
    
	@Column
	@Enumerated(EnumType.STRING)
    private ProductSellStatus productSellStatus;

    @Column(name = "product_type")
    private String productType;


    public void updateProduct(ProductFormDto productFormDto) {
        //웹에서 작성된 정보 itemFormDto에 저장됨. 저장된 것 getItemNm으로 꺼내서 this.itemNm에 넣기
        this.productTitle = productFormDto.getProductTitle();
        this.productPrice = productFormDto.getProductPrice();
        this.productQuantity = productFormDto.getProductQuantity();
        this.productContent = productFormDto.getProductContent();
        this.productSellStatus = productFormDto.getProductSellStatus();

    }
    
}
