package com.main.miniproject.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.main.miniproject.board.service.BoardImageService;
import com.main.miniproject.board.service.BoardService;
import com.main.miniproject.board.service.FileService;

@RestController
public class BoardRestController {
	
	@Autowired
	private BoardService boardService;

	@Autowired
	private BoardImageService boardImageService;
	
	@Autowired
	private FileService fileService;
	
	
    @DeleteMapping("/image/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        boardImageService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }
}

      
//      @PostMapping(value = "/api/board/insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//      public String saveBoard(@RequestPart("board") String boardString,
//                              @RequestPart("files") MultipartFile[] files) throws JsonProcessingException {
//
//        ObjectMapper objectMapper = new ObjectMapper();															// 임시 봉인
//        Board board = objectMapper.readValue(boardString, Board.class);
//
//        boardService.createBoard(board); // 게시글 저장
//
//        List<BoardImage> boardImages = fileService.saveFiles(board, files); // 파일 로직 호출
//
//        for (BoardImage boardImage : boardImages) { // List 데이터를 Entity에 주입
//          boardImageService.saveBoardImage(boardImage); // 저장
//        }
//
//        return "글이 작성되었습니다!";
//      }
//    }
	
	
	

