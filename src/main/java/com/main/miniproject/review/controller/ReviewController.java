package com.main.miniproject.review.controller;

import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.order.orderItemRepository.OrderItemRepository;
import com.main.miniproject.review.dto.ReviewDTO;
import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;
import com.main.miniproject.review.service.ReviewImageService;
import com.main.miniproject.review.service.ReviewService;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/review")
public class ReviewController {
    private ReviewService reviewService;

    private ReviewImageService reviewImageService;

    private UserService userService;

    private OrderItemRepository orderItemRepository;

    @Autowired
    public ReviewController(ReviewService reviewService,
                            ReviewImageService reviewImageService,
                            UserService userService,
                            OrderItemRepository orderItemRepository) {
        this.reviewService = reviewService;
        this.reviewImageService = reviewImageService;
        this.userService = userService;
        this.orderItemRepository = orderItemRepository;
    }

    //리뷰 글쓰기 페이지
    @GetMapping("/insert-view")
    public ModelAndView insertReviewView(@AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mv = new ModelAndView();
        User user = userService.getCurrentUser();

        //임시 리뷰 제목
        String productTitle = "Sample Product Title";
        mv.addObject("productTitle", productTitle);

        //리뷰 미작성 주문 상품 찾기
        List<OrderItem> notReviewedOrderItems = orderItemRepository.findNotReviewedOrderItemsByUserId(user.getId());

        mv.addObject("notReviewedOrderItems", notReviewedOrderItems);
        mv.addObject("review", new ReviewDTO());
        mv.setViewName("review/reviewInsert");

        return mv;
    }

    //리뷰 목록 페이지(상세 페이지 겸함)
    @GetMapping("/list")
    public ModelAndView getReviewList(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                      @AuthenticationPrincipal UserDetails userDetails) {
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

    //리뷰 목록 검색
    @GetMapping("/list-search")
    public ModelAndView searchReviewList(@RequestParam String keyword,
                                         @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                         HttpServletRequest request,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        userDetails.getUsername();
        ModelAndView mv = new ModelAndView();

        //검색어 저장
        HttpSession session = request.getSession();
        if(keyword != null && !keyword.isEmpty()){
            session.setAttribute("keyword", keyword);
        } else {
            keyword = (String) session.getAttribute("keyword");
        }

        Page<Review> reviewPage = reviewService.searchReview(pageable, keyword);

        Map<Long, List<ReviewImage>> reviewImageMap = new HashMap<>();
        for (Review review : reviewPage.getContent()) {
            List<ReviewImage> reviewImages = reviewImageService.reviewImageList(review);
            reviewImageMap.put(review.getId(), reviewImages);
        }

        mv.addObject("reviews", reviewPage.getContent());
        mv.addObject("page", reviewPage);
        mv.addObject("reviewImages", reviewImageMap);
        mv.setViewName("review/searchReviewList");

        return mv;
    }
}