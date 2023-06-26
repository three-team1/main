package com.main.miniproject.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.ProductDTO;
import com.main.miniproject.product.repository.ProductRepository;

@Service
public class ProductService {

	
	private ProductRepository productRepository;
	
	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<Product> getAllProducts() {
		
		return productRepository.findAll();
	}
	
	public Product getProductById (Long productId) {
		
		return productRepository.findById(productId).get();
	}
	
	public List<ProductDTO> searchProducts(String searchKeyword) {
		
		List<Product> productList = productRepository.findByProductTitleContaining(searchKeyword);
		
		List<ProductDTO> productDTOList = new ArrayList<>();
		
		for(Product product : productList) {
			
			ProductDTO productDTO = ProductDTO.builder()
								.productId(product.getId())
								.productTitle(product.getProductTitle())
								.productContent(product.getProductContent())
								.productPrice(product.getProductPrice())
								.productQuantity(product.getProductQuantity())
								.productType(product.getProductType())
								.build();
								
			productDTOList.add(productDTO);	
			
		}
		
		
		return productDTOList;
	}
}
