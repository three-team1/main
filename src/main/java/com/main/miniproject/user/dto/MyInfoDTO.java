package com.main.miniproject.user.dto;

import com.main.miniproject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyInfoDTO {
    private long id;
    private String username;
    @NotBlank
    private String password;
    private String email;
    private String tel;
    private String my_postcode;
    private String my_address;
    private String my_detailAddress;

    private String checkPassword;

    @NotBlank
    private String newPassword;
    @NotBlank
    private String confirmPassword;

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