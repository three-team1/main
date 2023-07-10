package com.main.miniproject.board.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.main.miniproject.board.service.BoardFileService;
import com.main.miniproject.board.service.BoardImageService;
import com.main.miniproject.board.service.BoardService;
import com.main.miniproject.comment.entity.Comment;
import com.main.miniproject.comment.service.CommentService;
import com.main.miniproject.user.service.UserDetail;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private BoardImageService boardImageService;

	@Autowired
	private BoardFileService boardfileService;
	
	@Autowired
	private CommentService commentService;


	@GetMapping("/board/write")
	public String showBoardWriteForm(@AuthenticationPrincipal UserDetail userDetail,Model model) {
		
		model.addAttribute("userDetail",userDetail);
		
		return "boardWrite";
	}

	@PostMapping("/board/insert")
	public String saveBoard(Board board, MultipartFile[] files, RedirectAttributes redirectAttributes) {

		System.out.println(files);

		boardService.createBoard(board);											// 게시글저장

		List<BoardImage> boardImages = boardfileService.saveFiles(board, files);			// 파일로직 호출

		for(BoardImage boardImage : boardImages) {									// List 데이터를 entity에 주입

			boardImageService.saveBoardImage(boardImage);							// 저장
		}

		redirectAttributes.addFlashAttribute("message","글이 작성되었습니다!");

		return "redirect:/board/list";
	}

	@GetMapping("/board/list")
	public String getBoardList(Model model,
							   @RequestParam(required = false) String keyword,
							   @PageableDefault(size = 15 , sort = "id", direction = Sort.Direction.DESC) Pageable pageable
							   ,@AuthenticationPrincipal UserDetail userDetail) {

		Page<Board> boardPage;
		if(keyword != null) {
			boardPage = boardService.searchBoard(pageable, keyword);
		} else {
			boardPage = boardService.getAllBoards(pageable);
		}

		model.addAttribute("userDetail",userDetail);
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
	public String getBoard(@PathVariable Long id, Model model,@AuthenticationPrincipal UserDetail userDetail
							) {
	    Board board = boardService.getBoard(id);
	    	     
	    List<BoardImage> boardImageList = boardImageService.boardImageList(board);
	    List<Comment> commentList = commentService.commentList(id, "community");
	    
	    Map<Long, List<Comment>> commentReplies = new HashMap<>();

	    for(Comment comment : commentList) {
	        List<Comment> replies = commentService.commentReplyList(comment.getId());
	        commentReplies.put(comment.getId(), replies);
	    }
	    	    
	    
	    model.addAttribute("userDetail",userDetail);
	    model.addAttribute("board", board);
	    model.addAttribute("images",boardImageList);
	    model.addAttribute("comments",commentList);
	    model.addAttribute("commentReplys",commentReplies);
	    
	    return "boardDetail";
	}


	@GetMapping("/board/edit/{id}")
	public String editBoard(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetail userDetail) {


		Board board = boardService.getBoard(id);
		List<BoardImage> boardImageList = boardImageService.boardImageList(board);

		model.addAttribute("userDetail",userDetail); 
		model.addAttribute("images",boardImageList);
		model.addAttribute("board",board);

		return "boardEdit";
	}

	@PostMapping("/board/update/{id}")
	public String updateBoard(@PathVariable Long id, Board board, RedirectAttributes redirectAttributes
			, MultipartFile[] files) {

		try {

			List<BoardImage> boardImages = boardfileService.saveFiles(board, files);				// 파일로직 호출

			for(BoardImage boardImage : boardImages) {
				boardImageService.saveBoardImage(boardImage);								//List를 Entity에 데이터주입

			}

			boardService.updateBoard(id, board);											// 최종 수정본 저장

		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/board/edit/" + id;
		}

		return "redirect:/board/list";

	}

}
