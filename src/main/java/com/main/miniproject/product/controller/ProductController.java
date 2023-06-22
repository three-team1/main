package com.main.miniproject.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.main.miniproject.product.entity.RealTimeSearch;
import com.main.miniproject.product.service.RealTimeSearchService;


@Controller
public class ProductController {

	
	@Autowired
	private RealTimeSearchService realTimeSearchService;
	
	
//	@Scheduled(fixedDelay = 15000)
//	public void scheduledKeyword() {
//		
//		realTimeSearchService.getTop10Searches();
//
//	}
	
	
	
	
	

	
	@GetMapping("/product/productList")
	public String searchList(String keyword, Model model) {
		
		realTimeSearchService.saveSearchKeyword(keyword);
		
		List<RealTimeSearch> realTimeTop10 = realTimeSearchService.getTop10Searches();

	    model.addAttribute("top10SearchList", realTimeTop10);
	    
	    return "/product/productList";
	    
	}
	
	
//	@GetMapping("/product/productList")
//	public String getProductList() {
//		
//		
//		return "/product/productList";
//	}
	
	
	
}
