package com.main.miniproject.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.main.miniproject.board.entity.Board;
import com.main.miniproject.board.entity.BoardImage;
import com.main.miniproject.board.service.BoardImageService;
import com.main.miniproject.board.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardImageService boardImageService;
	
	
	@GetMapping("/board/write")
	public String showBoardWriteForm() {
	    return "boardWrite";
	}
	
	@PostMapping("/board/insert") 
	public String saveBoard(Board board, MultipartFile[] files) {		
		
		System.out.println(files);
		
		boardService.createBoard(board);
		
		if(files != null && files.length > 0) {
			for(MultipartFile file : files) {
				if(!file.isEmpty()) {
				String fileOrigin = file.getOriginalFilename();
				String fileExt = fileOrigin.substring(fileOrigin.lastIndexOf("."));
				String fileName = UUID.randomUUID().toString() + fileExt;
				String filePath = "C:/board/images/" + fileName;
				
				try {
					
					file.transferTo(new File(filePath));
					
				}catch(IOException ie) {
					ie.getMessage();
				}
				
				BoardImage boardImage = new BoardImage();
				
				boardImage.setBoard(board);
				boardImage.setName(fileName);
				boardImage.setUrl(filePath);
				boardImage.setOriginName(fileOrigin);
				
			
				
				boardImageService.saveBoardImage(boardImage);
						
				}
			}
		}	
		return "redirect:/board/list";
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
	    List<BoardImage> boardImageList = boardImageService.boardImageList(board);
	    
	    model.addAttribute("images",boardImageList);
	    model.addAttribute("board", board);
	    return "boardDetail";
	}
	
	
	@GetMapping("/board/edit/{id}")
	public String editBoard(@PathVariable Long id, Model model) {
		
		
		Board board = boardService.getBoard(id);
		List<BoardImage> boardImageList = boardImageService.boardImageList(board);
	    
	    model.addAttribute("images",boardImageList);
		model.addAttribute("board",board);
		
		return "boardEdit";
	}
	
	@PostMapping("/board/update/{id}")
	public String updateBoard(@PathVariable Long id, Board board, RedirectAttributes redirectAttributes
			, MultipartFile[] files) {
		
	    try {
	        if (files != null && files.length > 0) {
	            for (MultipartFile file : files) {
	                if (!file.isEmpty()) {
	                    String fileOrigin = file.getOriginalFilename();
	                    String fileExt = fileOrigin.substring(fileOrigin.lastIndexOf("."));
	                    String fileName = UUID.randomUUID().toString() + fileExt;
	                    String filePath = "C:/board/images/" + fileName;

	                    try {
	                        file.transferTo(new File(filePath));
	                    } catch (IOException ie) {
	                        ie.getMessage();
	                    }

	                    BoardImage boardImage = new BoardImage();

	                    boardImage.setBoard(board);
	                    boardImage.setName(fileName);
	                    boardImage.setUrl(filePath);
	                    boardImage.setOriginName(fileOrigin);

	                    


	                    boardImageService.saveBoardImage(boardImage);

	                }
	            }
	        }
	        boardService.updateBoard(id, board);
	        
	    } catch (RuntimeException e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	        return "redirect:/board/edit/" + id;
	    }

	    return "redirect:/board/list";

	}
	
}
