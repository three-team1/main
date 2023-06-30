package com.main.miniproject.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.main.miniproject.user.dto.MyInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
@Table(
	    name="user",
	    uniqueConstraints={
	        @UniqueConstraint(
	            columnNames={"user_name", "provider"}  // local회원은 uq를 유지하면서 oauth회원은 중복되게.
	        )
	    }
	)
@Entity
public class User{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="user_id")
	private Long id;
	
	@Column(name="user_name", length = 50)
	private String username;
	
	@Column(name="user_pw", length = 200)
	private String password;
	
	@Column(name="user_email")
	private String email;
	
    @Column(name="profile_image")
    private String profileImage;
    
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;

    @Column
    private String provider;  // 'LOCAL', 'KAKAO'
    
    @Column
    private String providerId;  // 카카오 ID
    
    @Column(name="user_tel")
    private String tel;
    
    @Column(name="user_address1")
    private String my_postcode;
    
    @Column(name="user_address2")
    private String my_address;
    
    @Column(name="user_address3")
    private String my_detailAddress;
    

    
    public String getRoleKey() {
        return this.role.getKey();
    }

    public MyInfoDTO EntityToDTO() {
        MyInfoDTO myInfoDTO = MyInfoDTO.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .tel(this.tel)
                .my_postcode(this.my_postcode)
                .my_address(this.my_address)
                .my_detailAddress(this.my_detailAddress)
                .build();
        return myInfoDTO;
    }

    public void updatePassword(String confirmPassword) {
        this.password = confirmPassword;
    }
}
