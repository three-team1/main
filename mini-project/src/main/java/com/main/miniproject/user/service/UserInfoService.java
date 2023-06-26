package com.main.miniproject.user.service;

import com.main.miniproject.user.dto.MyInfoDTO;
import com.main.miniproject.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserInfoService {
    public MyInfoDTO getMyInfo(String username);

    void updateMyInfo(MyInfoDTO myInfoDTO);

    UserDetails updateUserDetails(UserDetails currentUserDetails);

    User getUserByUsername(String username);
}