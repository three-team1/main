package com.main.miniproject.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.miniproject.board.entity.Board;
import com.main.miniproject.board.entity.BoardImage;
import com.main.miniproject.board.repository.BoardImageRepository;

@Service
public class BoardImageService {

	@Autowired
	private BoardImageRepository boardImageRepository;
	
	
	public List<BoardImage> boardImageList(Board board) {
		
		return boardImageRepository.findByBoard(board);
	}
	
	public void saveBoardImage(BoardImage boardImage) {
		boardImageRepository.save(boardImage);
	}
	
	public void deleteImage(Long imageId) {
		
		boardImageRepository.deleteById(imageId);
	}
}
