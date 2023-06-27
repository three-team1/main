package com.main.miniproject.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ProductController {

	@GetMapping("/product/productList")
	public String getProductList() {
		
		
		return "/product/productList";

	}
	
}
