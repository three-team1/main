package com.main.miniproject.comment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.miniproject.comment.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findByBoardIdAndBoardType(Long boardId, String boardType);


	List<Comment> findByUserUsername(String username);
	
}
