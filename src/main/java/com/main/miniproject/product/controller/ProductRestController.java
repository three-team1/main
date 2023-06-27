package com.main.miniproject.product.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.ProductImage;
import com.main.miniproject.product.service.ProductImageService;
import com.main.miniproject.product.service.ProductService;

@RestController
public class ProductRestController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductImageService productImageService;
	
	@GetMapping("/api/products")
	public List<Product> getProducts() {
	    return productService.getAllProducts();
	}
	
	@GetMapping("/api/productImages/{productId}")
	public List<ProductImage> getProductImages(@PathVariable Long productId) {
		Product product = productService.getProductById(productId);
		
		return productImageService.getProductImagesByProduct(product);
	}
	

	
}
