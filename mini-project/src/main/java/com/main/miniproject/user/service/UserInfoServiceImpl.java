package com.main.miniproject.user.service;

import com.main.miniproject.user.dto.MyInfoDTO;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Autowired
    public UserInfoServiceImpl(UserRepository userRepository,
                               PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    //내 정보 조회
    @Override
    public MyInfoDTO getMyInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return MyInfoDTO.of(user);
    }

    //내 정보 수정
    @Override
    @Transactional
    public void updateMyInfo(MyInfoDTO myInfoDTO) {
        String username = myInfoDTO.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        user.setEmail(myInfoDTO.getEmail());
        user.setTel(myInfoDTO.getTel());
        user.setMy_postcode(myInfoDTO.getMy_postcode());
        user.setMy_address(myInfoDTO.getMy_address());
        user.setMy_detailAddress(myInfoDTO.getMy_detailAddress());

        userRepository.save(user);
    }

    //내 정보 수정 시 로그인 풀림 방지용
    @Override
    public UserDetails updateUserDetails(UserDetails currentUserDetails) {
        //UserDetail로 객체화 여부
        if(!(currentUserDetails instanceof UserDetail)) {
            throw new IllegalArgumentException("UserDetail 객체가 아닙니다.");
        }

        String username = currentUserDetails.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        UserDetail newUserDetails = new UserDetail((UserDetail) currentUserDetails);
        newUserDetails.setUser(user);

        return newUserDetails;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return user;
    }
}