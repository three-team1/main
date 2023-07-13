package com.main.miniproject.board.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.main.miniproject.board.entity.Board;
import com.main.miniproject.board.entity.BoardImage;

@Service
public class BoardFileService {

    @Value("${boardImgLocation}")
    private String boardImgLocation;

    public List<BoardImage> saveFiles(Board board, MultipartFile[] files) {
        
        List<BoardImage> boardImages = new ArrayList<>();
        
        for(MultipartFile file : files) {
            
            if(!file.isEmpty()) {
                
                String fileOrigin = file.getOriginalFilename();
                String fileExt = fileOrigin.substring(fileOrigin.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + fileExt;
                String filePath = Paths.get(boardImgLocation, fileName).toString();
                
                try {
                    
                    file.transferTo(new File(filePath));
                
                }catch (IOException ie) {
                    ie.getStackTrace();
                }
                
                BoardImage boardImage = BoardImage.builder()
                        .board(board)
                        .name(fileName)
                        .originName(fileOrigin)
                        .url(filePath)
                        .build();
                
                boardImages.add(boardImage);
                
            }
            
        }
    
    
    return boardImages;
    }
}

	
	

