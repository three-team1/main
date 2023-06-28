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
import com.main.miniproject.board.service.BoardFileService;
import com.main.miniproject.board.service.BoardImageService;
import com.main.miniproject.board.service.BoardService;
import com.main.miniproject.board.service.CommentService;

@RestController
public class BoardRestController {
	

	@Autowired
	private BoardImageService boardImageService;
	
	
    @DeleteMapping("/image/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        boardImageService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }
    
    
}

      
	
	
	

