package com.main.miniproject.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.main.miniproject.board.entity.Board;
import com.main.miniproject.board.entity.Comment;
import com.main.miniproject.board.service.BoardService;
import com.main.miniproject.board.service.CommentService;

@RestController
public class CommentRestController {

	
	private CommentService commentService;
	
	
	private BoardService boardService;
	
	@Autowired
	public CommentRestController(CommentService commentService, BoardService boardService) {
		this.commentService = commentService;
		this.boardService = boardService;
	}
	
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
    	commentService.deleteComment(commentId);
    	return ResponseEntity.ok().build();
    	
    }
    
    @PostMapping("/board/comment/{boardId}")
    public ResponseEntity<?> postComment(@PathVariable Long boardId, @RequestBody Comment comment) {
        Board board = boardService.getBoard(boardId);
        comment.setBoard(board);
        commentService.saveComment(comment);
        return ResponseEntity.ok().build();
    }
   
	
}
