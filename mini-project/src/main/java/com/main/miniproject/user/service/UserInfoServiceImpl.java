package com.main.miniproject.user.service;

import com.main.miniproject.user.dto.MyInfoDTO;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return MyInfoDTO.of(user);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    //내 정보 수정
    @Override
    @Transactional
    public void updateMyInfo(String username, MyInfoDTO myInfoDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            //현재 비밀번호 일치 여부 확인
/*            if (!encoder.matches(myInfoDTO.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException("비밀번호 불일치");
            }

            //비밀번호 불일치 시 변경 방지
            if (myInfoDTO.getNewPassword() != null && !myInfoDTO.getNewPassword().isEmpty()) {
                    String encPassword = encoder.encode(myInfoDTO.getNewPassword());
                    user.setPassword(encPassword);
            }

 */
            //비밀번호 null 변경 방지
            String encPassword = myInfoDTO.getPassword() != null
                    ? encoder.encode(myInfoDTO.getPassword()) : user.getPassword();

            user.setPassword(encPassword);
            user.setEmail(myInfoDTO.getEmail());
            user.setTel(myInfoDTO.getTel());
            user.setMy_postcode(myInfoDTO.getMy_postcode());
            user.setMy_address(myInfoDTO.getMy_address());
            user.setMy_detailAddress(myInfoDTO.getMy_detailAddress());

            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    //내 정보 수정 시 로그인 풀림 방지용
    @Override
    public UserDetails updateUserDetails(String username, UserDetails currentUserDetails) {
        //UserDetail로 객체화 여부
        if(!(currentUserDetails instanceof UserDetail)) {
            throw new IllegalArgumentException("UserDetail 객체가 아닙니다.");
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDetail newUserDetails = new UserDetail((UserDetail) currentUserDetails);
            newUserDetails.setUser(user);
            return newUserDetails;
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
