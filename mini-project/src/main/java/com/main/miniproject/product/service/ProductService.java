package com.main.miniproject.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.miniproject.product.entity.Product;
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
}
