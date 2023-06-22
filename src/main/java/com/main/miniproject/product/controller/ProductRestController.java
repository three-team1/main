package com.main.miniproject.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.ProductImage;
import com.main.miniproject.product.entity.RealTimeSearch;
import com.main.miniproject.product.service.ProductImageService;
import com.main.miniproject.product.service.ProductService;
import com.main.miniproject.product.service.RealTimeSearchService;

@RestController
public class ProductRestController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductImageService productImageService;
	
	@Autowired
	private RealTimeSearchService realTimeSearchService;
	
	@GetMapping("/api/products")
	public List<Product> getProducts() {
	    return productService.getAllProducts();
	}
	
	@GetMapping("/api/productImages/{productId}")
	public List<ProductImage> getProductImages(@PathVariable Long productId) {
		Product product = productService.getProductById(productId);
		
		return productImageService.getProductImagesByProduct(product);
	}
	
	
	@GetMapping("/api/top10searches")
	public ResponseEntity<List<RealTimeSearch>> getTop10Searches() {
	    List<RealTimeSearch> realTimeTop10 = realTimeSearchService.getTop10Searches();
	    return ResponseEntity.ok(realTimeTop10);
	    
	}
	
}
