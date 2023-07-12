package com.main.miniproject.user.controller;

import com.main.miniproject.product.dto.ProductFormDto;
import com.main.miniproject.user.dto.UserFormDto;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberAdminController {

    @Autowired
    private final UserService userService;

    //관리자 페이지 메인
    @GetMapping("/admin")
    public String adminPage(Model model){
        model.addAttribute("itemFormDto", new ProductFormDto());
        return "admin/mainPage";
    }


    //회원관리 탭에서 회원목록 가져오기 ( + 페이징 기능, 검색 기능 )
    @GetMapping("/admin/members")
    public String memberList(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "keyword", defaultValue = "") String keyword,
                             @RequestParam(value = "category", defaultValue = "") String category) {
        Page<User> userPage = userService.getList(page, keyword, category);

        model.addAttribute("userPage", userPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);

        return "member/memberList";
    }

    //회원 상세정보 조회
    @GetMapping("/admin/memberDetail/{memberId}")
    public String memberDetail(@PathVariable("memberId") Long memberId, Model model){

        User user = userService.getUserDetail(memberId);
        model.addAttribute("UserFormDto", user);
        return "member/memberForm";

    }

    //회원 삭제
    @PostMapping("/admin/memberDetail/{memberId}")
    public String deleteUser(@PathVariable("memberId") Long id) {

        userService.deleteUser(id);

        return "redirect:/admin/members";

    }

    //회원 정보 수정
    @PostMapping ("/admin/memberDetail/updateMember/{memberId}")
    public String updateUser(@PathVariable("memberId") Long id ,UserFormDto userFormDto){

        userService.updateUser(userFormDto);


        return "redirect:/admin/memberDetail/{memberId}";
    }





}