package com.main.miniproject.user.service;

import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Transactional(readOnly=true)
public class UserInfoServiceImpl implements UserInfoService {
    private UserRepository userRepository;

    private PasswordEncoder encoder;

    private UserService userService;

    @Autowired
    public UserInfoServiceImpl(UserRepository userRepository,
                               PasswordEncoder encoder,
                               UserService userService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userService = userService;
    }

    //내 정보 조회
    @Override
    public User getMyInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return user;
    }

    //내 정보 수정
    @Override
    @Transactional
    public void updateMyInfo(User user, HttpSession session) {
        String username = user.getUsername();

        User updatedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        updatedUser.setEmail(user.getEmail());
        updatedUser.setTel(user.getTel());
        updatedUser.setMy_postcode(user.getMy_postcode());
        updatedUser.setMy_address(user.getMy_address());
        updatedUser.setMy_detailAddress(user.getMy_detailAddress());

        userRepository.save(updatedUser);

        //수정된 정보 security에 업데이트
        UserDetail userDetail = (UserDetail) userService.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

    //비밀번호 확인
    @Override
    public boolean checkPassword(String username, String checkPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String realPassword = user.getPassword();

        boolean matches = encoder.matches(checkPassword, realPassword);

        return matches;
    }

    //비밀번호 변경
    @Override
    @Transactional
    public boolean changePassword(String username, String password, String newPassword, String confirmPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String realPassword = user.getPassword();

        if (encoder.matches(password, realPassword)) {
            if (newPassword.equals(confirmPassword)) {
                user.updatePassword(encoder.encode(confirmPassword));
                userRepository.save(user);

                return true;
            }
        }

        return false;
    }

}