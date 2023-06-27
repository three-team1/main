package com.main.miniproject.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.miniproject.board.entity.Board;
import com.main.miniproject.board.entity.BoardImage;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long>{

	List<BoardImage> findByBoard(Board board);
	
}
