package com.main.miniproject.review.controller;

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

        mv.setViewName("review/reviewInsert.html");

        return mv;
    }
}
