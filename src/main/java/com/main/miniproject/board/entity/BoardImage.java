package com.main.miniproject.board.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
	private List<BoardImage> images = new ArrayList<>();
	
}
