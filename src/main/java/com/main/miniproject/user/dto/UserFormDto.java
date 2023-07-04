package com.main.miniproject.user.dto;

import com.main.miniproject.user.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@ToString
public class UserFormDto {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String provider;  // 'LOCAL', 'KAKAO'

    private String providerId;  // 카카오 ID

    private String tel;

    private String my_postcode;

    private String my_address;

    private String my_detailAddress;

    private String role;

}
