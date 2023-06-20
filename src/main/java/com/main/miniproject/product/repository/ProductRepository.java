package com.main.miniproject.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.miniproject.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
