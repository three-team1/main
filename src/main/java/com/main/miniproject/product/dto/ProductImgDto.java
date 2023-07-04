package com.main.miniproject.product.dto;

import com.main.miniproject.product.entity.ProductImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
public class ProductImgDto {

	private Long id;

	private String productName; //(선택사항 by 양재민)
	
	private String name;
	
	private String url;
	
	private String originName;
	
	//modelmapper: dto객체와 entity객체의 변환을 도움.
	//서로 다른 클래스의 값을 필드의 이름과 자료형이 같으면 getter, setter를 통해 값을 복사해서 객체를 반환.
	private static ModelMapper modelMapper = new ModelMapper();
	
	//entity 받으면 dto로 변환
	public static ProductImgDto of(ProductImage productImage) {
		return modelMapper.map(productImage, ProductImgDto.class);
	}
}
