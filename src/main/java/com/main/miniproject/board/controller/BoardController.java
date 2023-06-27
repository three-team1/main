package com.main.miniproject.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.main.miniproject.board.entity.Board;
import com.main.miniproject.board.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	
	@GetMapping("/board/write")
	public String showBoardWriteForm() {
	    return "boardWrite";
	}
	
	@GetMapping("/board/list")
	public String getBoardList(Model model, 
			@RequestParam(required = false) String keyword, 
			@PageableDefault(size = 15 , sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		
		Page<Board> boardPage;
		
		if(keyword != null) {
			boardPage = boardService.searchBoard(pageable, keyword);
		} else {
			boardPage = boardService.getAllBoards(pageable);
		}
		
	    model.addAttribute("boards", boardPage);
	    model.addAttribute("page", boardPage);
		
		return "boardList";
	}
	
	@PostMapping("/board/list") 
	public String saveBoard(Board board) {
		
		boardService.createBoard(board);
		
		return "redirect:/board/list";
	}
	
	@GetMapping("/board/delete/{id}")
	public String deleteBoard(@PathVariable Long id, RedirectAttributes redirectAttributes ) {
	    

	    try {
	        boardService.deleteBoard(id);
	        return "redirect:/board/list";
	    } catch (RuntimeException e) {
	    	redirectAttributes.addFlashAttribute("error", e.getMessage());
	        return "redirect:/board/detail/" + id;
	    }
	   
	}
	
	@GetMapping("/board/detail/{id}")
	public String getBoard(@PathVariable Long id, Model model) {
	    Board board = boardService.getBoard(id);
	    model.addAttribute("board", board);
	    return "boardDetail";
	}
	
	
	@GetMapping("/board/edit/{id}")
	public String editBoard(@PathVariable Long id, Model model) {
		
		
		Board board = boardService.getBoard(id);
		
		model.addAttribute("board",board);
		
		return "boardEdit";
	}
	
	@PostMapping("/board/update/{id}")
	public String updateBoard(@PathVariable Long id, Board board, RedirectAttributes redirectAttributes) {
		
	    try {
	        boardService.updateBoard(id, board);
	    } catch (RuntimeException e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	        return "redirect:/board/edit/" + id;
	    }
	    
	    return "redirect:/board/list";
		
	}
	
}
