package com.main.miniproject.review.service.impl;

import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;
import com.main.miniproject.review.service.ReviewFileService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

    @Value("/review/images/")
    String attachPath;

    @Value("/review/resized/")
    String resizedPath;

    @Override
    @Transactional
    public List<ReviewImage> saveFiles(Review review, List<MultipartFile> files) {

        List<ReviewImage> reviewImages = new ArrayList<>();

        //업로드 일자 기준으로 파일 저장 폴더 생성
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String dataPath = Paths.get(attachPath, today).toString();
        String resizedDatePath = Paths.get(resizedPath, today).toString();

        File dir = new File(dataPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File resizedDir = new File(resizedDatePath);
        if (!resizedDir.exists()) {
            resizedDir.mkdirs();
        }

        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                //원본 이미지
                String fileOrigin = file.getOriginalFilename();
                String fileExt = fileOrigin.substring(fileOrigin.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + fileExt;
                String filePath = Paths.get(dataPath, fileName).toString();

                //축소 이미지
                String smallFileName = UUID.randomUUID().toString() + fileExt;
                String smallFilePath = Paths.get(resizedDatePath, smallFileName).toString();

                //리사이징 된 높이를 담을 변수 선언
                BufferedImage smallImage = null;
                int height = 0;

                try {
                    //원본 이미지 가로 길이 확인
                    File originFile = new File(filePath);
                    file.transferTo(originFile);

                    BufferedImage originImage = ImageIO.read(new File(filePath));
                    int originWidth = originImage.getWidth();

                    //축소 이미지 저장
                    if (originWidth >= 500) {
                        smallImage = Thumbnails.of(originImage)
                                .width(500)
                                .keepAspectRatio(true)
                                .asBufferedImage();
                    } else {
                        smallImage = originImage;
                    }

                    ImageIO.write(smallImage, fileExt.substring(1), new File(smallFilePath));

                    height = smallImage.getHeight();

                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }

                //이미지 파일 상대경로로 저장하기
                String smallFileURL = "/review/resized" + File.separator + today + File.separator + smallFileName;

                //축소 이미지 객체
                if (smallImage != null && height != 0) {
                    ReviewImage smallReviewImage = ReviewImage.builder()
                            .review(review)
                            .name(smallFileName)
                            .originName(fileName)
                            .url(smallFileURL)
                            .height(height)
                            .build();

                    reviewImages.add(smallReviewImage);
                }
            }
        }
        return reviewImages;
    }
}