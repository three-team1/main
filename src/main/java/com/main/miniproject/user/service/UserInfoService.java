package com.main.miniproject.user.service;

import com.main.miniproject.user.dto.MyInfoDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoService {
    public MyInfoDTO getMyInfo(String username);

    void updateMyInfo(String username, MyInfoDTO myInfoDTO);

    UserDetails updateUserDetails(String username, UserDetails currentUserDetails);
}
