package com.main.miniproject.comment.entity;

import javax.persistence.*;

import com.main.miniproject.qna.entity.QNA;
import com.main.miniproject.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;
	
	@Column(name = "comment_content")
	private String commentContent;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column( name = "board_type")
	private String boardType;
	
	@Column(name = "board_id")
	private Long boardId;

	@Column(name = "parent_comment_id")
	private Long parentCommentId;

	
	
	
}
