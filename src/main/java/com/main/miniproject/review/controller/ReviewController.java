package com.main.miniproject.review.controller;

import com.main.miniproject.review.dto.ReviewDTO;
import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;
import com.main.miniproject.review.service.ReviewImageService;
import com.main.miniproject.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/review")
public class ReviewController {
    private ReviewService reviewService;

    private ReviewImageService reviewImageService;

    @Autowired
    public ReviewController(ReviewService reviewService,
                            ReviewImageService reviewImageService) {
        this.reviewService = reviewService;
        this.reviewImageService = reviewImageService;
    }

    //리뷰 글쓰기 페이지
    @GetMapping("/insert-view")
    public ModelAndView insertReviewView() {
        ModelAndView mv = new ModelAndView();

        //임시 리뷰 제목
        String productTitle = "Sample Product Title";
        mv.addObject("productTitle", productTitle);

        mv.addObject("review", new ReviewDTO());
        mv.setViewName("review/reviewInsert");

        return mv;
    }

    //리뷰 목록 페이지(상세 페이지 겸함)
    @GetMapping("/list")
    public ModelAndView getReviewList(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC)
                                      Pageable pageable, @AuthenticationPrincipal UserDetails userDetails) {
        userDetails.getUsername();
        ModelAndView mv = new ModelAndView();

        Page<Review> reviewPage = reviewService.getAllReviews(pageable);

        //각 리뷰의 이미지 List를 담아줄 map 선언
        Map<Long, List<ReviewImage>> reviewImageMap = new HashMap<>();

        //각 리뷰에 이미지를 담고 map에 저장
        for (Review review : reviewPage.getContent()) {
            List<ReviewImage> reviewImages = reviewImageService.reviewImageList(review);
            reviewImageMap.put(review.getId(), reviewImages);
        }

        mv.addObject("reviews", reviewPage);
        mv.addObject("page", reviewPage);
        mv.addObject("reviewImages", reviewImageMap);
        mv.setViewName("review/reviewList");

        return mv;
    }
}
