package com.main.miniproject.user.service;

import com.main.miniproject.user.dto.MyInfoDTO;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserCRUD {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

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

    @Override
    @Transactional
    public void updateMyInfo(String username, MyInfoDTO myInfoDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

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
}
