package com.main.miniproject.user.controller;

import com.main.miniproject.user.dto.MyInfoDTO;
import com.main.miniproject.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
public class MypageRestController {
    private UserInfoService userInfoService;

    @Autowired
    public MypageRestController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    //내 정보 조회
    @GetMapping("/myInfo")
    public MyInfoDTO getMyInfo(@AuthenticationPrincipal UserDetails userDetails) throws UsernameNotFoundException {
        String username = userDetails.getUsername();
        MyInfoDTO myInfoDTO = userInfoService.getMyInfo(username);
        return myInfoDTO;
    }

    //내 정보 수정
    @PutMapping("/myInfo")
    public void updateMyInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MyInfoDTO myInfoDTO) throws UsernameNotFoundException {
        String username = userDetails.getUsername();

        userInfoService.updateMyInfo(myInfoDTO);

        //내 정보 수정 시 수정된 사용자 정보 로그아웃 없이 반영
        UserDetails updatedUserDetails = userInfoService.updateUserDetails(userDetails);

        Authentication authentication = new UsernamePasswordAuthenticationToken(updatedUserDetails,
                myInfoDTO.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
