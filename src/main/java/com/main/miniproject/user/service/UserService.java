package com.main.miniproject.user.service;

import com.main.miniproject.user.dto.UserFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

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
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    //페이징 처리
    //page는 현재페이지. 숫자는 현재 페이지에 나타낼 레코드의 갯수
    public Page<User> getList(int page, String keyword, String category) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("role"));
        sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));
        Specification<User> userSpecification = search(keyword, category);


        return userRepository.findAll(userSpecification, pageable);


    }

    //키워드 검색
    private Specification<User> search(String keyword, String category){
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> userRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true);

                List<Predicate> predicates = new ArrayList<>();


                if(!StringUtils.isEmpty(keyword)) {
                    switch (category) {
                        case "username":
                            predicates.add(criteriaBuilder.like(userRoot.get("username"), "%" + keyword + "%"));
                            break;
                        case "email":
                            predicates.add(criteriaBuilder.like(userRoot.get("email"), "%" + keyword + "%"));
                            break;
                        case "tel":
                            predicates.add(criteriaBuilder.like(userRoot.get("tel"), "%" + keyword + "%"));
                            break;
                        default:
                            predicates.add(criteriaBuilder.or(
                                    criteriaBuilder.like(userRoot.get("username"), "%" + keyword + "%"),
                                    criteriaBuilder.like(userRoot.get("email"), "%" + keyword + "%"),
                                    criteriaBuilder.like(userRoot.get("tel"), "%" + keyword + "%")
                            ));
                            break;
                    }
                }else{
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.like(userRoot.get("username"), "%" + keyword + "%"),
                            criteriaBuilder.like(userRoot.get("email"), "%" + keyword + "%"),
                            criteriaBuilder.like(userRoot.get("tel"), "%" + keyword + "%")
                    ));
                }

                System.out.println("===============keyword================="+keyword);
                System.out.println("==============category================="+category);

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
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