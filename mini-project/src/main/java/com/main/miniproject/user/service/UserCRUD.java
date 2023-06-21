package com.main.miniproject.user.service;

import com.main.miniproject.user.dto.MyInfoDTO;

public interface UserCRUD {
    public MyInfoDTO getMyInfo(String username);

    void updateMyInfo(String username, MyInfoDTO myInfoDTO);
}
