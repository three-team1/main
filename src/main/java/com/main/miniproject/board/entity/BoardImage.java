package com.main.miniproject.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_img_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;
	
	@Column(name = "board_img_name")
	private String name;
	
	@Column(name = "board_img_url")
	private String url;
	
	@Column(name = "board_img_ori_name")
	private String originName;
	
}
