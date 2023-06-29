package com.main.miniproject;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.main.miniproject.user.service.UserDetail;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String main(Model model, @AuthenticationPrincipal UserDetail userDetail) {
		model.addAttribute("userDetail", userDetail);
		return "main";
	}
	
}
