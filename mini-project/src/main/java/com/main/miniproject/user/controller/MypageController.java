package com.main.miniproject.user.controller;

import com.main.miniproject.user.dto.MyInfoDTO;
import com.main.miniproject.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Controller
@RequestMapping("/mypage")
public class MypageController {
    @Autowired
    private UserInfoService userInfoService;

    public MypageController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    //마이페이지
    @GetMapping("/me")
    public String mypageView() {
        return "mypage/me";
    }

    //마이페이지 내 정보 조회
    @GetMapping("/myInfo")
    public String getMyInfo(@AuthenticationPrincipal UserDetails userDetails, Model model) throws UsernameNotFoundException {
        String username = userDetails.getUsername(); // 현재 로그인한 사용자의 username을 가져옵니다.
        MyInfoDTO myInfoDTO = userInfoService.getMyInfo(username);
        model.addAttribute("user", myInfoDTO);
        return "mypage/myInfo";
    }

}