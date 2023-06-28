package com.main.miniproject.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.main.miniproject.board.entity.Board;
import com.main.miniproject.board.entity.Comment;
import com.main.miniproject.board.repository.CommentRepository;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserDetail;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	
	public List<Comment> commentList(Board board) {
		
		return commentRepository.findByBoard(board);
	}
	
	public void saveComment(Comment comment) {
		
		User user = getCurrentUser();
		
		comment.setUser(user);
		
		commentRepository.save(comment);
		
	}
	
	
	public User getCurrentUser() {											// 사용자 인증정보 반환
		
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserDetail userDetail = (UserDetail) authentication.getPrincipal();
	return userDetail.getUser();
	
	}
	
}
