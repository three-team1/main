package com.main.miniproject.comment.repository;

import java.util.List;

import com.main.miniproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.miniproject.comment.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findByBoardIdAndBoardType(Long boardId, String boardType);
	
	List<Comment> findByParentCommentId(Long parentCommentId);

	List<Comment> findByUserUsername(String username);

	void deleteByUser(User user);
}
