package com.main.miniproject.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.main.miniproject.comment.entity.Comment;
import com.main.miniproject.comment.repository.CommentRepository;
import com.main.miniproject.user.entity.Role;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserDetail;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	
	public List<Comment> commentList(Long boardId, String boardType) {
		
		return commentRepository.findByBoardIdAndBoardType(boardId, boardType);
				
	}
	
	public void saveComment(Comment comment, Long boardId, String boardType) {
	    User user = getCurrentUser();
	    comment.setUser(user);
	    comment.setBoardId(boardId);
	    comment.setBoardType(boardType);
	    commentRepository.save(comment);
	}
	
	public void deleteComment(Long commentId) {
		
		User user = getCurrentUser();
		
		Comment comment = commentRepository.findById(commentId).get();
		
        if (!comment.getUser().getUsername().equals(user.getUsername()) && user.getRole() != Role.ADMIN) {
            throw new RuntimeException("권한이 없습니다.");
        }
		commentRepository.deleteById(commentId);
	}
	
	
	
	public User getCurrentUser() {											// 사용자 인증정보 반환
		
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserDetail userDetail = (UserDetail) authentication.getPrincipal();
	return userDetail.getUser();
	
	}
	
}
