package com.main.miniproject.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.miniproject.product.entity.RealTimeSearch;

@Repository
public interface RealTimeSearchRepository extends JpaRepository<RealTimeSearch, String>{

			RealTimeSearch findBysearchKeyword(String searchKeyword);
	
	
}
