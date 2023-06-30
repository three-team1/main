package com.main.miniproject.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.miniproject.product.entity.Product;

import com.main.miniproject.product.dto.ProductDTO;


import com.main.miniproject.product.entity.ProductImage;
import com.main.miniproject.product.entity.RealTimeSearch;
import com.main.miniproject.product.repository.RealTimeSearchRepository;
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
	
	@Autowired
	private RealTimeSearchRepository realTimeSearchRepository;

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
	public List<RealTimeSearch> getTop10Searches() {

		List<RealTimeSearch> top10Searches = realTimeSearchService.getTop10Searches();
			
	    return top10Searches;
	}
	
	@PostMapping("/api/updateSearches")
	public void updateSearches() {

		List<RealTimeSearch> top10Searches = realTimeSearchService.getTop10Searches();
		
		for(RealTimeSearch realTimeSearch : top10Searches) {
			if(realTimeSearch.isSearchUpdate()) {
				realTimeSearch.setSearchUpdate(false);
				realTimeSearchRepository.save(realTimeSearch);
			}
		}
	}
	
	@PostMapping("/api/searches")
	public List<RealTimeSearch> saveSearchKeywordAndGetTopSearches(@RequestParam String searchKeyword) {
		
	    realTimeSearchService.saveSearchKeyword(searchKeyword);
	    
	    return realTimeSearchService.getTop10Searches();
	    
	}
	
	
	@GetMapping("/api/products/search")
	public List<ProductDTO> searchProducts(@RequestParam String searchKeyword) {
	    return productService.searchProducts(searchKeyword);
	}
}
