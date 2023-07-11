package com.main.miniproject.review.service.impl;

import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;
import com.main.miniproject.review.service.ReviewFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewFileServiceImpl implements ReviewFileService {

    @Value("C:/review/images/")
    String attachPath;

    @Override
    @Transactional
    public List<ReviewImage> saveFiles(Review review, MultipartFile[] files) {

        List<ReviewImage> reviewImages = new ArrayList<>();

        //업로드 일자 기준으로 파일 저장 폴더 생성
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String datePath = Paths.get(attachPath, today).toString();

        File dir = new File(datePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileOrigin = file.getOriginalFilename();
                String fileExt = fileOrigin.substring(fileOrigin.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + fileExt;
                String filePath = Paths.get(datePath, fileName).toString();

                try {
                    file.transferTo(new File(filePath));
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }

                //이미지 파일 상대경로로 저장하기
                String fileURL = "/review/images" + File.separator + today + File.separator + fileName;

                ReviewImage reviewImage = ReviewImage.builder()
                        .review(review)
                        .name(fileName)
                        .originName(fileOrigin)
                        .url(fileURL)
                        .build();

                reviewImages.add(reviewImage);
            }
        }
        return reviewImages;
    }
}