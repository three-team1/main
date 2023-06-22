package com.main.miniproject.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.RealTimeSearch;
import com.main.miniproject.product.service.ProductService;
import com.main.miniproject.product.service.RealTimeSearchService;

import lombok.extern.log4j.Log4j2;


@Log4j2
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
	public String searchList() {
			
		realTimeSearchService.getTop10Searches();
		
	    return "/product/productList";
	    
	}
	


	
}
