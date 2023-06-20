package com.main.miniproject.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.miniproject.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
	
	Page<Board> findByBoardTitleContainingOrBoardContentContaining(String title, String content, Pageable pageable);
}
