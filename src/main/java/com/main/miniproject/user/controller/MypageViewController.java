package com.main.miniproject.user.controller;

import com.main.miniproject.board.entity.Board;
import com.main.miniproject.board.service.BoardService;
import com.main.miniproject.comment.entity.Comment;
import com.main.miniproject.comment.service.CommentService;
import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.service.OrdersService;
import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;
import com.main.miniproject.review.service.ReviewFileService;
import com.main.miniproject.review.service.ReviewImageService;
import com.main.miniproject.review.service.ReviewService;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserInfoService;
import com.main.miniproject.user.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MypageViewController {
    private UserInfoService userInfoService;

    private UserService userService;

    private ReviewService reviewService;

    private ReviewImageService reviewImageService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    public MypageViewController(UserInfoService userInfoService,
                                UserService userService,
                                ReviewService reviewService,
                                ReviewImageService reviewImageService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.reviewImageService = reviewImageService;
    }

    //마이페이지 주문/배송 조회 페이지
    @GetMapping("/me")
    public ModelAndView mypageView(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mv = new ModelAndView();

        User user = userInfoService.getMyInfo(userDetails.getUsername());
//        List<Orders> ordersList = ordersService.getOrdersList(user.getId());
//        List<OrderItem> orderItemList = ordersService.getOrderItemList(user);

//        model.addAttribute("orders", ordersList);
//        model.addAttribute("orderitems", orderItemList);

        mv.setViewName("/mypage/me");

        return mv;
    }

    //마이페이지 내 정보 관리 페이지 + 일반 로그인 회원, 소셜 로그인 회원 구분
    @GetMapping("/myInfo")
    public ModelAndView myInfoView() {
        ModelAndView mv = new ModelAndView();
        User user = userService.getCurrentUser();

        if (user.getProvider() == null) {
            mv.setViewName("/mypage/myInfo");
        } else {
            mv.setViewName("/mypage/myInfoOAuth");
        }

        return mv;
    }

    //비밀번호 확인 페이지
    @GetMapping("/pwChk")
    public ModelAndView pwChkView() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("/mypage/pwChk");

        return mv;
    }

    //내 정보 수정 페이지 + 일반 로그인 회원, 소셜 로그인 회원 구분
    @GetMapping("/myUpdate")
    public ModelAndView myUpdateView(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userInfoService.getMyInfo(userDetails.getUsername());
        ModelAndView mv = new ModelAndView();

        mv.addObject("user", user);

        if (user.getProvider() == null) {
            mv.setViewName("/mypage/myUpdate");
        } else {
            mv.setViewName("/mypage/myUpdateOAuth");
        }

        return mv;
    }

    //비밀번호 수정 페이지
    @GetMapping("/pwChange")
    public ModelAndView pwChangeView() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("/mypage/pwChange");

        return mv;
    }

    //내 글 조회
    @GetMapping("/myBoard")
    public String myBoardView(Model model,
                              @AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam(required = false) String keyword,
                              @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Board> boardPage;
        List<Comment> commentList = commentService.getMypagecomments(userDetails.getUsername());


        User user = userInfoService.getMyInfo(userDetails.getUsername());
        if(keyword != null) {
            boardPage = boardService.searchBoard(pageable, keyword);
        } else {
            boardPage = boardService.getMypageBoards(user.getUsername(), pageable);
        }

        model.addAttribute("boards", boardPage);
        model.addAttribute("page", boardPage);
        model.addAttribute("comments",commentList);

        return "mypage/myBoard";
    }

    //내 리뷰 조회
    @GetMapping("/myReview")
    public ModelAndView myReviewPage(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mv = new ModelAndView();

        //현재 로그인 사용자의 리뷰 가져오기
        String username = userDetails.getUsername();
        Page<Review> reviewPage = reviewService.getMyReviews(pageable, username);

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
        mv.setViewName("/mypage/myReview");

        return mv;
    }

    //내 리뷰 검색
    @GetMapping("/myReview-search")
    public ModelAndView searchMyReview(@RequestParam String keyword,
                                       @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mv = new ModelAndView();

        //현재 로그인한 사용자의 리뷰만 가져오는 로직 추가
        String username = userDetails.getUsername();


        Page<Review> reviewPage = reviewService.searchMyReviews(pageable, username, keyword);

        //각 리뷰의 이미지 List를 담아줄 map 선언
        Map<Long, List<ReviewImage>> reviewImageMap = new HashMap<>();

        for (Review review : reviewPage.getContent()) {
            List<ReviewImage> reviewImages = reviewImageService.reviewImageList(review);
            reviewImageMap.put(review.getId(), reviewImages);
        }

        mv.addObject("reviews", reviewPage);
        mv.addObject("page", reviewPage);
        mv.addObject("reviewImages", reviewImageMap);
        mv.addObject("keyword", keyword);
        mv.setViewName("/mypage/searchMyReview");

        return mv;
    }
}

