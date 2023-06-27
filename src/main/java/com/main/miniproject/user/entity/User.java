package com.main.miniproject.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "user")
@Entity
public class User{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="user_id")
	private Long id;
	
	@Column(name="user_name", unique = true, length = 50)
	private String username;
	
	@Column(name="user_pw", length = 200)
	private String password;
	
	@Column(name="user_email")
	private String email;
	
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
}
