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
	
	public void saveSearchKeyword(String searchKeyword) {

		RealTimeSearch realTimeSearchKeyword =  realTimeSearchRepository.findBysearchKeyword(searchKeyword);
			
		if(realTimeSearchKeyword == null) {
			
			realTimeSearchKeyword = new RealTimeSearch();
			
			realTimeSearchKeyword.setSearchKeyword(searchKeyword);

			realTimeSearchRepository.save(realTimeSearchKeyword);
			
		} else {
			
			realTimeSearchKeyword.setSearchCnt(realTimeSearchKeyword.getSearchCnt() + 1);
			
			
			realTimeSearchRepository.save(realTimeSearchKeyword);
		}
	}
	
	
	public List<RealTimeSearch> searchList () {
	
		return realTimeSearchRepository.findAll();
	}
	
	public List<RealTimeSearch> getTop10Searches() {
		
		return realTimeSearchRepository.findTop10ByOrderBySearchCntDesc();
	}
	
	
}
