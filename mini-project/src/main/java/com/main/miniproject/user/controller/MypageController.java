package com.main.miniproject.user.controller;

import com.main.miniproject.user.dto.MyInfoDTO;
import com.main.miniproject.user.service.UserInfoService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final UserInfoService userInfoService;

    //마이페이지
    @GetMapping("/me")
    public String mypageView() {
        return "mypage/me";
    }

    //내 정보 조회
    @GetMapping("/myInfo")
    public String getMyInfo(@AuthenticationPrincipal UserDetails userDetails, Model model) throws UsernameNotFoundException {
        String username = userDetails.getUsername();
        MyInfoDTO myInfoDTO = userInfoService.getMyInfo(username);
        model.addAttribute("user", myInfoDTO);
        return "mypage/myInfo";
    }

    //내 정보 수정 페이지
    @GetMapping("/myUpdate")
    public String myUpdateView(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        MyInfoDTO myInfoDTO = userInfoService.getMyInfo(username);
        model.addAttribute("user", myInfoDTO);
        return "mypage/myUpdate";
    }

    //내 정보 수정
    @PostMapping("/myUpdate")
    public String updateMyInfo(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute MyInfoDTO myInfoDTO) throws UsernameNotFoundException {
        String username = userDetails.getUsername();

        userInfoService.updateMyInfo(username, myInfoDTO);

        return "redirect:/mypage/myInfo";
    }

}