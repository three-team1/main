package com.main.miniproject.user.controller;

import java.util.Optional;

import com.main.miniproject.user.dto.UserDtoAdmin;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberAdminController {

    private final UserService userService;

    //회원관리 탭에서 회원목록 가져오기
    @GetMapping({"/admin/members", "/admin/members/{page}"})	//페이지 정보 없는 것, 있는 것 둘 다 처리 가능.
    public String memberList(UserDtoAdmin userDtoAdmin, Model model,
                             //페이지 정보를 들고 올 수도 있고 페이지 정보가 없을 수도 있다.
                             @PathVariable("page") Optional<Integer> page) {
        //시작페이지는 페이지가 있으면 get()한 페이지 들고 오고 아니면 0으로 하겠다. 한 페이지에 상품은 3개씩
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<User> members = userService.getAdminMemberPage(userDtoAdmin, pageable);

        model.addAttribute("members", members);
        model.addAttribute("memberDto", userDtoAdmin);
        model.addAttribute("maxPage", 5);
        model.addAttribute("totalPages", members.getTotalPages());

        return "member/memberList";
    }
}