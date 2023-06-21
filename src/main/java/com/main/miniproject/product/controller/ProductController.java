package com.main.miniproject.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.main.miniproject.product.entity.RealTimeSearch;
import com.main.miniproject.product.service.RealTimeSearchService;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
public class ProductController {
	
	
	 
	
	@Autowired
	private RealTimeSearchService realTimeSearchService;
	
	
	@Scheduled(fixedDelay = 1000)
	public String scheduledKeyword() {
		
		List<RealTimeSearch> realTimeSearchList = realTimeSearchService.searchList();
		log.info(realTimeSearchList);
		
		return "redirect:/product/productList";
	}
	
	
	
	@GetMapping("/product")
	public String searchList() {

		RealTimeSearch realTimeSearch = new RealTimeSearch();
		
		String keyword = "나나나";

		realTimeSearchService.saveSearchKeyword(keyword);		
		
//	    List<RealTimeSearch> realTimeSearchList = realTimeSearchService.searchList();

//	    model.addAttribute("searchList", realTimeSearchList);
	    
	    return "redirect:/product/productList";
	    
	}
	
	
	
	

	@GetMapping("/product/productList")
	public String getProductList() {
		
		
		return "/product/productList";

	}
	
}
