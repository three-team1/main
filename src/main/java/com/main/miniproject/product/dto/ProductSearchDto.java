package com.main.miniproject.product.dto;

import com.main.miniproject.product.entity.ProductSellStatus;
import lombok.Data;

@Data
public class ProductSearchDto {

    //상품 판매 상태
    private ProductSellStatus searchSellStatus;

    //어떤 방법으로 찾을지(구분)
    private String searchBy;

    //검색어
    private String searchQuery = "";


}
