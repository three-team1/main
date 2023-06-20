package com.main.miniproject.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.main.miniproject.board.entity.Board;
import com.main.miniproject.board.repository.BoardRepository;
import com.main.miniproject.user.entity.Role;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserDetail;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;	
	
	public Board createBoard(Board board) {											//게시글 등록

		User user = getCurrentUser();
		
		board.setUser(user);
		
		return boardRepository.save(board);
	}
	
	
	public Board saveBoard(Board board) {
		
		return boardRepository.save(board);
	}
	
	
	public Board updateBoard(Long id,Board boardDetails) {											// 게시글 수정
		
		User user = getCurrentUser();
		
		Board board = getBoard(id);
		
		if (!board.getUser().getUsername().equals(user.getUsername()) && user.getRole() != Role.ADMIN) {
	        throw new RuntimeException("You don't have permission to update this board");
	    }
	
		board.setBoardTitle(boardDetails.getBoardTitle());
        board.setBoardContent(boardDetails.getBoardContent());
        
        return saveBoard(board);
		
	}
	
	public void deleteBoard(Long id) {												// 게시글 삭제

	    User user = getCurrentUser();

	    Board board = getBoard(id);

	    if (!board.getUser().getUsername().equals(user.getUsername()) && user.getRole() != Role.ADMIN) {
	        throw new RuntimeException("You don't have permission to delete this board");
	    }
	    
		boardRepository.deleteById(id);
	}
	
	
	public Board getBoard(Long id) {									// 사용자가 해당 게시글에 접속할때
		
		Board board = boardRepository.findById(id).orElseThrow(
				() -> new RuntimeException("not found"));
		
		board.setBoardView(board.getBoardView() + 1);
	    boardRepository.save(board);
	    return board;
	}
	
	public Page<Board> getAllBoards(Pageable pageable) {                  //페이징 처리후 게시글 목록 출력 
		
		return boardRepository.findAll(pageable);
	}
	
	public Page<Board> searchBoard(Pageable pageable, String keyword){    // 게시글 검색에따른 게시글 출력
		return boardRepository.findByBoardTitleContainingOrBoardContentContaining(keyword, keyword, pageable);
	}
	
	
	public User getCurrentUser() {											// 사용자 인증정보 반환
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserDetail userDetail = (UserDetail) authentication.getPrincipal();
	return userDetail.getUser();
	
	}
}
