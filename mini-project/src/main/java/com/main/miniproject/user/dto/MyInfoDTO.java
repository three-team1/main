package com.main.miniproject.user.dto;

import com.main.miniproject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyInfoDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String tel;
    private String my_postcode;
    private String my_address;
    private String my_detailAddress;

    public static MyInfoDTO of(User user) {
        MyInfoDTO myInfoDTO = new MyInfoDTO();

        myInfoDTO.id = user.getId();
        myInfoDTO.username = user.getUsername();
        myInfoDTO.password = user.getPassword();
        myInfoDTO.email = user.getEmail();
        myInfoDTO.tel = user.getTel();
        myInfoDTO.my_postcode = user.getMy_postcode();
        myInfoDTO.my_address = user.getMy_address();
        myInfoDTO.my_detailAddress = user.getMy_detailAddress();

        return myInfoDTO;
    }
}