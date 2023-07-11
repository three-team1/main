package com.main.miniproject.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.miniproject.review.dto.ReviewDTO;
import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;
import com.main.miniproject.review.service.ReviewFileService;
import com.main.miniproject.review.service.ReviewImageService;
import com.main.miniproject.review.service.ReviewService;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
public class ReviewRestController {
    private ReviewService reviewService;

    private ReviewImageService reviewImageService;

    private ReviewFileService reviewFileService;

    private UserService userService;

    @Autowired
    public ReviewRestController(ReviewService reviewService,
                                ReviewImageService reviewImageService,
                                ReviewFileService reviewFileService,
                                UserService userService) {
        this.reviewService = reviewService;
        this.reviewImageService = reviewImageService;
        this.reviewFileService = reviewFileService;
        this.userService = userService;
    }

    //리뷰 작성
    @PostMapping("/myReview")
    public ResponseEntity<Map<String, String>> insertReview(@RequestPart("jsonBlob") String jsonBlob,
                                                            @RequestPart(value = "files", required = false) List<MultipartFile> files) throws Exception {
        //jsonBlob -> JSON 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(jsonBlob, Map.class);

        Integer reviewRating = Integer.parseInt(jsonMap.get("reviewRating").toString());
        String reviewContent = jsonMap.get("reviewContent").toString();
        Long orderItemId = Long.parseLong(jsonMap.get("orderItemId").toString());

        User user = userService.getCurrentUser();

        ReviewDTO reviewDTO = new ReviewDTO(reviewContent, reviewRating, user.getId(), orderItemId);

        ReviewDTO savedReviewDTO = reviewService.insertReview(user.getId(), orderItemId, reviewDTO);
        Review savedReview = savedReviewDTO.DTOToEntity();

        List<ReviewImage> reviewImages = new ArrayList<>();

        if(files != null && files.size() > 0) {
            reviewImages = reviewFileService.saveFiles(savedReview, files);

            for(ReviewImage reviewImage : reviewImages) {
                reviewImageService.saveReviewImage(reviewImage);
            }
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "리뷰가 성공적으로 작성되었습니다.");
        response.put("redirect", "/review/list");
        response.put("imageCount", String.valueOf(reviewImages.size()));
        response.put("firstImageUrl", reviewImages.isEmpty() ? "" : reviewImages.get(0).getUrl());

        return ResponseEntity.ok(response);
    }

    // 리뷰 삭제
    @DeleteMapping("myReview/{id}")
    public ResponseEntity<Map<String, Object>> deleteReview (@PathVariable Long id,
                                                             HttpServletRequest request,
                                                             RedirectAttributes redirectAttributes) {
        Map<String, Object> response = new HashMap<>();
        try {
            reviewService.deleteReview(id);
            String redirectPage = request.getHeader("redirectPage");
            response.put("status", "success");
            response.put("message", "리뷰를 삭제했습니다.");
            response.put("redirectPage", redirectPage);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}