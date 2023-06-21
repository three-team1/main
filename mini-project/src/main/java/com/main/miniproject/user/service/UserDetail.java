package com.main.miniproject.user.service;

import java.util.Arrays;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.main.miniproject.user.entity.Role;
import com.main.miniproject.user.entity.User;

public class UserDetail implements UserDetails, OAuth2User{

	private User user;
	private Role role;
	private Map<String, Object> attributes;
	
	

	public UserDetail(User user) {
		super();
		this.user = user;
		this.role = user.getRole();
	}
	
    public UserDetail(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
        this.role = user.getRole();
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		switch (role) {
        case ADMIN:
            return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        case VIP:
            return Arrays.asList(new SimpleGrantedAuthority("ROLE_VIP"));
        default:
            return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
	
	public User getUser() {
		return this.user;
	}
	
	 public Map<String, Object> getAttributes() {
	        return attributes;
	 }
	 
	@Override
	public String getName() {
			
		return this.getUsername();
	}
	
	@Override
	public String getPassword() {

		return user.getPassword();
	}

	@Override
	public String getUsername() {

		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}



}
