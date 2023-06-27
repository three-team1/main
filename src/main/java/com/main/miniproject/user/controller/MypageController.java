package com.main.miniproject.user.controller;

import com.main.miniproject.user.dto.MyInfoDTO;
import com.main.miniproject.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageController {
    private UserInfoService userInfoService;

    @Autowired
    public MypageController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    //마이페이지
    @GetMapping("/me")
    public String mypageView() {
        return "mypage/me";
    }

    @GetMapping("")
    public String mypagetest() {
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
    @GetMapping("/myUpdate-view")
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

        //내 정보 수정 시 수정된 사용자 정보 로그아웃 없이 반영
        UserDetails updatedUserDetails = userInfoService.updateUserDetails(username, userDetails);

        Authentication authentication = new UsernamePasswordAuthenticationToken(updatedUserDetails,
                myInfoDTO.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/mypage/myInfo";
    }

    //비밀번호 수정 페이지(test용)
    @GetMapping("/pwChange")
    public  String pwChangeView() {
        return "/mypage/pwChange";
    }
}