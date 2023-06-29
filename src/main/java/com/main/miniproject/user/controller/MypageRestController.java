package com.main.miniproject.user.controller;

import com.main.miniproject.user.dto.MyInfoDTO;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
    public User getMyInfo(@AuthenticationPrincipal UserDetails userDetails) throws UsernameNotFoundException {
        return userInfoService.getMyInfo(userDetails.getUsername());
    }

    //내 정보 수정
    @PutMapping("/myInfo")
    public void updateMyInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MyInfoDTO myInfoDTO, HttpSession session) throws UsernameNotFoundException {
        User curUser = userInfoService.getMyInfo(userDetails.getUsername());

        if(!curUser.getUsername().equals(myInfoDTO.getUsername())) {
            throw new IllegalArgumentException("유저 불일치");
        }

        User user = myInfoDTO.DTOToEntity();

        userInfoService.updateMyInfo(user, session);
    }

    //내 비밀번호 확인
    @GetMapping("/pwCheck")
    public boolean checkPassword(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("checkPassword") String checkPassword, Model model) {
        String username = userDetails.getUsername();

        return userInfoService.checkPassword(username, checkPassword);
    }

    //비밀번호 변경
    @PostMapping("/myInfo")
    @Transactional
    public boolean changePassword(@AuthenticationPrincipal UserDetails userDetails,
                                                  @Valid @RequestBody MyInfoDTO myInfoDTO) {
        String username = userDetails.getUsername();

        return userInfoService.changePassword(username, myInfoDTO.getPassword(), myInfoDTO.getNewPassword(), myInfoDTO.getConfirmPassword());
    }
}
