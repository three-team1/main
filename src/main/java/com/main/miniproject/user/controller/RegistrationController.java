package com.main.miniproject.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserService;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user",new User());
		return "register";
	}
	
	@PostMapping("/register")
	public String createUser(User user, Model model) {
	    try {
	        userService.createUser(user); 
	        return "redirect:/login";  
	    } catch (DataIntegrityViolationException e) {
	        model.addAttribute("errorMessage", "이미 존재하는 회원입니다");
	        return "register";
	    }
	}
	
}
