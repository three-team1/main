package com.main.miniproject.product.dto;

import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.ProductImage;
import com.main.miniproject.product.entity.ProductSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductFormDto {

    private Long id; // 상품 코드
    
    private String productType; //상품 카테고리
    
    @NotBlank(message = "상품명은 필수 항목입니다.")
    private String productTitle; // 상품 이름

    @NotNull(message = "가격은 필수 항목입니다.")
    private Double productPrice; // 상품 가격

    @NotNull(message = "재고는 필수 항목입니다.")
    private int productQuantity; // 재고 수량

    private ProductSellStatus productSellStatus; // 상품 판매 상태

    @NotBlank(message = "상품 설명은 필수 항목입니다.")
    private String productContent; // 상품 상세 설명

    private LocalDateTime regtime;

    private List<ProductImgDto> productImgDtoList = new ArrayList<>();

    //아이디 정보를 받아오려고 함
    private List<Long> productImgIds = new ArrayList<>();


    //modelmapper: dto객체와 entity객체의 변환을 도움.
    //서로 다른 클래스의 값을 필드의 이름과 자료형이 같으면 getter, setter를 통해 값을 복사해서 객체를 반환.
    private static ModelMapper modelMapper = new ModelMapper();



    //Item 타입으로 리턴(dto에서 entity로 변환)
    public Product createProduct() {

        return modelMapper.map(this, Product.class);
    }

    //entity 받으면 dto로 변환
    public static ProductFormDto of(Product product) {

        return modelMapper.map(product, ProductFormDto.class);
    }

}
