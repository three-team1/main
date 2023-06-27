package com.main.miniproject.product.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.miniproject.product.entity.RealTimeSearch;

@Repository
public interface RealTimeSearchRepository extends JpaRepository<RealTimeSearch, Long>{

			RealTimeSearch findBysearchKeyword(String searchKeyword);


			List<RealTimeSearch> findTop10ByOrderBySearchCntDesc();

}
