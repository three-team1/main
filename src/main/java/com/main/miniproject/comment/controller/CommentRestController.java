package com.main.miniproject.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.main.miniproject.comment.entity.Comment;
import com.main.miniproject.comment.service.CommentService;

@RestController
public class CommentRestController {

	
	private CommentService commentService;
	
	@Autowired
	public CommentRestController(CommentService commentService) {
		this.commentService = commentService;
	}
	
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
    	commentService.deleteComment(commentId);
    	return ResponseEntity.ok().build();
    	
    }
    
    @PostMapping("/board/comment/{boardId}")
    public ResponseEntity<?> postComment(@PathVariable Long boardId, @RequestBody Comment comment) {
    
        String boardType = "community";
        commentService.saveComment(comment,boardId,boardType);
        return ResponseEntity.ok().build();
    }
   
	
}
