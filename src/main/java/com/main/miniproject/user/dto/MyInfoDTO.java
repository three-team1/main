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
    private long id;
    private String username;
    private String password;
    private String email;
    private String tel;
    private String my_postcode;
    private String my_address;
    private String my_detailAddress;

    private String checkPassword;

    private String newPassword;
    private String confirmPassword;

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

    public User DTOToEntity() {
        User user = User.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .tel(this.tel)
                .my_postcode(this.my_postcode)
                .my_address(this.my_address)
                .my_detailAddress(this.my_detailAddress)
                .build();

        return user;
    }
}