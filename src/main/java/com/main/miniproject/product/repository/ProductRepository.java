package com.main.miniproject.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.miniproject.product.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom{

    List<Product> findByProductTitle(String productTitle);

    List<Product> findByProductTitleOrProductContent(String productTitle, String productContent);

    List<Product> findByProductTitleContaining (String searchKeyword);


}
