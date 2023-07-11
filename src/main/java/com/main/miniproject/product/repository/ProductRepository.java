package com.main.miniproject.product.repository;

import com.main.miniproject.product.entity.ProductSellStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.main.miniproject.product.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{


    List<Product> findByProductTitleContaining (String searchKeyword);


    Page<Product> findAll(Specification<Product> productSpecification, Pageable pageable);

}
