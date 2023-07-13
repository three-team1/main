package com.main.miniproject.qna.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import com.main.miniproject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QNA {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "qna_id")
	private Long id;
	
	@Column(name = "qna_title")
	private String qnaTitle;

	@Column(name = "qna_writer")
	private String qnaWriter;
	
	@Column(name = "qna_content")
	private String qnaContent;
	
	@Column(name = "qna_regdate")
	private LocalDateTime qnaRegdate = LocalDateTime.now();

	@Column(name="qna_Cnt", nullable = false)
	private int qnaCnt = 0;
	
	@Column(name = "qna_response")
	private String qnaResponse;
	
	@Column(name = "qna_boolean")
	private Boolean qnaBoolean;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	//댓글 개수
	@Formula("(SELECT count(1) FROM comment c WHERE c.board_id = qna_id and c.board_type = 'qna')")
	private int replyCount;


}
