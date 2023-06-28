package com.main.miniproject.user.controller;

import com.main.miniproject.board.entity.Board;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mypage")
public class MypageViewController {
    private UserInfoService userInfoService;

    @Autowired
    public MypageViewController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/me")
    public String mypageView() {
        return "mypage/me";
    }

    @GetMapping("/myInfo")
    public String myInfoView() {
        return "mypage/myInfo";
    }

    @GetMapping("/myUpdate")
    public String myUpdateView(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userInfoService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "mypage/myUpdate";
    }
    @GetMapping("/myBoard")
    public String myBoardView() {

        return "mypage/myboard";
    }

    /*@GetMapping("/board/list")
    public String getBoardList(Model model,
                               @RequestParam(required = false) String keyword,
                               @PageableDefault(size = 15 , sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Board> boardPage;
        if(keyword != null) {
            boardPage = boardService.searchBoard(pageable, keyword);
        } else {
            boardPage = boardService.getAllBoards(pageable);
        }

        model.addAttribute("boards", boardPage);
        model.addAttribute("page", boardPage);

        return "boardList";
    }*/

}
