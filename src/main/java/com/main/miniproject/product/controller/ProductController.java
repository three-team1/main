package com.main.miniproject.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.async.DeferredResult;

import com.main.miniproject.product.entity.RealTimeSearch;
import com.main.miniproject.product.service.RealTimeSearchService;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
public class ProductController {
	
	@Autowired
	private RealTimeSearchService realTimeSearchService;	
	
	@Scheduled(fixedDelay = 1500)
	public void updateSearchList() {
		
		realTimeSearchService.getTop10Searches();
	}
	 
	
	
	
	@GetMapping("/product/productList")
	public String searchList(String keyword, Model model) {

		realTimeSearchService.saveSearchKeyword(keyword);		
		
	    List<RealTimeSearch> realTimeTop10 = realTimeSearchService.getTop10Searches();

	    model.addAttribute("searchList", realTimeTop10);
	    
	    return "/product/productList";
	    
	}
	
//	@GetMapping("/product/productList")
//	public String getProductList() {
//		
//		
//		return "/product/productList";
//
//	}
	
}
