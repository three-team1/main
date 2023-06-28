package com.main.miniproject.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.main.miniproject.board.entity.Comment;
import com.main.miniproject.board.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/board/comment")
	public String createComment(Comment comment) {
		
		commentService.saveComment(comment);
		
		return "reidrect:/board/detail";
	}
	
}
