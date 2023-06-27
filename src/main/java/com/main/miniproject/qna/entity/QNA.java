package com.main.miniproject.qna.entity;

import com.main.miniproject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
	
	
	
	
	
	
}
