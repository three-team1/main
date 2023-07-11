package com.main.miniproject.user.service;

import com.main.miniproject.user.dto.UserDtoAdmin;
import com.main.miniproject.user.dto.UserFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.main.miniproject.user.entity.Role;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.repository.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("not"));

        return new UserDetail(user);
    }

    public User createUser(User user) {

        user.setRole(Role.USER);
        user.setProvider("local");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    //memberRepositoryCustom, memberRepositoryCustomImpl에서 작성한 쿼리문 출력하기
    public Page<User> getAdminMemberPage(UserDtoAdmin userDtoAdmin, Pageable pageable){
        return userRepository.getAdminMemberPage(userDtoAdmin, pageable);
    }

    //회원 상세 정보 조회
    public User getUserDetail(Long memberId){
       return userRepository.findById(memberId).get();

    }

    //회원 삭제
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    //회원 수정
    public void updateUser(UserFormDto userFormDto) {
        User user = userRepository.findById(userFormDto.getId()).orElseThrow(EntityNotFoundException::new);

        user.setUsername(userFormDto.getUsername());
        user.setEmail(userFormDto.getEmail());
        user.setTel(userFormDto.getTel());
        user.setMy_postcode(userFormDto.getMy_postcode());
        user.setMy_address(userFormDto.getMy_address());
        user.setMy_detailAddress(userFormDto.getMy_detailAddress());


        userRepository.save(user);
    }

    //로그인 유저 확인
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        return userRepository.findByUsername(userDetail.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("유저 없음"));
    }

}