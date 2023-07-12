package com.main.miniproject.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.ProductImage;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>{

	List<ProductImage> findByProduct(Product product);

	List<ProductImage> findByName(ProductImage productImage);

}
