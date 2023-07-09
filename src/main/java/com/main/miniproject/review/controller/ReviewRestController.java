package com.main.miniproject.review.controller;

import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;
import com.main.miniproject.review.service.ReviewFileService;
import com.main.miniproject.review.service.ReviewImageService;
import com.main.miniproject.review.service.ReviewService;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserDetail;
import com.main.miniproject.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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

    private UserInfoService userInfoService;

    @Autowired
    public ReviewRestController(ReviewService reviewService,
                                ReviewImageService reviewImageService,
                                ReviewFileService reviewFileService,
                                UserInfoService userInfoService) {
        this.reviewService = reviewService;
        this.reviewImageService = reviewImageService;
        this.reviewFileService = reviewFileService;
        this.userInfoService = userInfoService;
    }

    //리뷰 작성
    @PostMapping("/myReview")
    public ResponseEntity<Map<String, String>> insertReview(@RequestPart("reviewRating") String reviewRating,
                                                            @RequestPart("reviewContent") String reviewContent,
                                                            @RequestPart(value = "files", required = false) MultipartFile[] files,
                                                            RedirectAttributes redirectAttributes,
                                                            @AuthenticationPrincipal UserDetails userDetails) {

        User user = getCurrentUser();

        //임시 데이터
        OrderItem orderItem = OrderItem.builder().id(1L).build();

        Review review = Review.builder()
                .reviewContent(reviewContent)
                .reviewRating(Integer.parseInt(reviewRating))
                .reviewRegdate(LocalDateTime.now())
                .user(user)
                .orderItem(orderItem)
                .build();

        reviewService.insertReview(review);

        List<ReviewImage> reviewImages = new ArrayList<>();

        if(files != null && files.length > 0) {
            reviewImages = reviewFileService.saveFiles(review, files);

            for(ReviewImage reviewImage : reviewImages) {
                reviewImageService.saveReviewImage(reviewImage);
            }
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "리뷰가 작성되었습니다.");
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


    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return userDetail.getUser();
    }

}