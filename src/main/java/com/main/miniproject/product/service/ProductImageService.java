package com.main.miniproject.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.ProductImage;
import com.main.miniproject.product.repository.ProductImageRepository;

@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Autowired
    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public List<ProductImage> getAllProductImages() {
    	
        return productImageRepository.findAll();
    }
	
    public List<ProductImage> getProductImagesByProduct(Product product) {
    	
    	return productImageRepository.findByProduct(product);
    }
    
}
