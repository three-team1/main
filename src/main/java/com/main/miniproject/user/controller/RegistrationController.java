package com.main.miniproject.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
	
	@PostMapping("/register") // ajax
	public ResponseEntity<?> createUser(@RequestBody User user) {
	    try {
	        userService.createUser(user);    
	        return ResponseEntity.ok().build();
	    } catch (DataIntegrityViolationException e) {
	        return ResponseEntity.badRequest().body("이미 가입한 회원입니다.");
	    }
	}
	
}
