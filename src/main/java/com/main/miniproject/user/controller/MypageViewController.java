package com.main.miniproject.user.controller;

import com.main.miniproject.board.entity.Board;
import com.main.miniproject.board.service.BoardService;
import com.main.miniproject.comment.entity.Comment;
import com.main.miniproject.comment.service.CommentService;
import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.service.OrdersService;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserInfoService;
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

import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MypageViewController {
    private UserInfoService userInfoService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    public MypageViewController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    //마이페이지 주문/배송 조회 페이지
    @GetMapping("/me")
    public String mypageView(Model model, @AuthenticationPrincipal UserDetails userDetails) {


        User user = userInfoService.getMyInfo(userDetails.getUsername());


        List<Orders> ordersList = ordersService.getOrdersList(user);

        List<Orders> prodList = ordersService.getProductsList(user.getId());

        model.addAttribute("orders", ordersList);
        model.addAttribute("products", prodList);

        return "mypage/me";
    }
    /*List<Orders> orders = orderService.getOrdersByUserId(userDetail.getId())

    model.addAttribute "orders",orders*/


    //마이페이지 내 정보 관리 페이지
    @GetMapping("/myInfo")
    public ModelAndView myInfoView() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("/mypage/myInfo.html");

        return mv;
    }

    //비밀번호 확인 페이지
    @GetMapping("/pwChk")
    public ModelAndView pwChkView() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("/mypage/pwChk.html");

        return mv;
    }

    //내 정보 수정 페이지
    @GetMapping("/myUpdate")
    public ModelAndView myUpdateView(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userInfoService.getMyInfo(userDetails.getUsername());
        ModelAndView mv = new ModelAndView();

        mv.addObject("user", user);
        mv.setViewName("/mypage/myUpdate.html");

        return mv;
    }

    //비밀번호 수정 페이지
    @GetMapping("/pwChange")
    public ModelAndView pwChangeView() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("/mypage/pwChange.html");

        return mv;
    }

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


}

