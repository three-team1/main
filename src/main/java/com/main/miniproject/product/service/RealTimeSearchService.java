package com.main.miniproject.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.miniproject.product.entity.RealTimeSearch;
import com.main.miniproject.product.repository.RealTimeSearchRepository;

@Service
public class RealTimeSearchService {

	@Autowired
	private RealTimeSearchRepository realTimeSearchRepository;
	
	private RealTimeSearch realTimeSearch;
	
	public void saveSearchKeyword(String searchKeyword) {

		System.out.println(searchKeyword);
		RealTimeSearch realTimeSearchKeyword =  realTimeSearchRepository.findBysearchKeyword(searchKeyword);
		
		
		
		if(realTimeSearchKeyword == null) {
			
			realTimeSearchKeyword = new RealTimeSearch();
			
			Long intr =10L;		
//			realTimeSearchKeyword.setId(intr);
			realTimeSearchKeyword.setSearchKeyword(searchKeyword);
			System.out.println(realTimeSearchKeyword.toString());
			realTimeSearchRepository.save(realTimeSearchKeyword);
			
		} else {
			
			realTimeSearchKeyword.setSearchCnt(realTimeSearchKeyword.getSearchCnt() + 1);
			
			
			realTimeSearchRepository.save(realTimeSearchKeyword);
		}
	}
	
	
	public List<RealTimeSearch> searchList () {
	
		return realTimeSearchRepository.findAll();
	}
	
	
}
