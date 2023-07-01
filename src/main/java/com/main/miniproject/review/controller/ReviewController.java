package com.main.miniproject.review.controller;

import com.main.miniproject.review.dto.ReviewDTO;
import com.main.miniproject.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/review")
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
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
}
