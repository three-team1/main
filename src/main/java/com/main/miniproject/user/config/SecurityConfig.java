package com.main.miniproject.user.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.main.miniproject.user.service.CustomOAuth2UserService;
import com.main.miniproject.user.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter{

		@Autowired
		private UserService userService;
		
		@Autowired
	    private PasswordEncoder passwordEncoder;
		
		@Autowired
	    private CustomOAuth2UserService customOAuth2UserService;
			
		@Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	            .csrf().disable()
	            .authorizeRequests()
	                .antMatchers("/login", "/register","/","/api/products","/api/productImages/**").permitAll()
	                .antMatchers("/board/list").permitAll()
	                .antMatchers("/css/**", "/videos/**","/images/**", "/icon/**", "/json/**","/scss/**","/js/**").permitAll()
	                .antMatchers("/admin/**").hasRole("ADMIN")			// 로그인, 회원가입 페이지는 누구나 접근 가능
	                .anyRequest().authenticated()                      // 그 외 페이지는 인증된 사용자만 접근 가능
	            .and()
	            .formLogin()  // 로그인 폼 설정
	                .loginPage("/login")  // 로그인 페이지 URL
	                .defaultSuccessUrl("/",true)  // 로그인 성공 시 리다이렉트할 페이지 URL
	                .failureHandler(new AuthenticationFailureHandler() {
	                    @Override
	                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	                                                        AuthenticationException exception) throws IOException, ServletException {
	                        request.getSession().setAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다");
	                        response.sendRedirect("/login");
	                    }
	                })
	            .and()	
	            .logout()  // 로그아웃 설정
	                .logoutSuccessUrl("/");  // 로그아웃 성공 시 리다이렉트할 페이지 URL

	        Kakao(http);
	                  
	    }
			
		private void Kakao(HttpSecurity http) throws Exception {
		     http
	            .oauth2Login()
	                .userInfoEndpoint()
	                    .userService(customOAuth2UserService)
	                .and()
	                .defaultSuccessUrl("/", true)
	                .loginPage("/login")
	                .permitAll();
		    
		    
		}
}
