package com.main.miniproject.user.service;

import com.main.miniproject.user.dto.MyInfoDTO;
import com.main.miniproject.user.entity.User;

import javax.servlet.http.HttpSession;

public interface UserInfoService {
    User getMyInfo(String username);

    void updateMyInfo(MyInfoDTO myInfoDTO, HttpSession session);

    boolean checkPassword(String username, String checkPassword);
}