package com.main.miniproject.user.repository;

import com.main.miniproject.user.dto.UserDtoAdmin;
import com.main.miniproject.user.entity.QUser;
import com.main.miniproject.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public UserRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<User> getAdminMemberPage(UserDtoAdmin userDtoAdmin, Pageable pageable) {

        List<User> content = queryFactory.selectFrom(QUser.user)
                .where(searchByLike(userDtoAdmin.getSearchBy(), userDtoAdmin.getSearchQuery()))
                //정렬과 페이지 시작할 위치.
                .orderBy(QUser.user.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(Wildcard.count).from(QUser.user)
                .where(searchByLike(userDtoAdmin.getSearchBy(), userDtoAdmin.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);

    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        //이름으로 검색
        if(StringUtils.equals("username", searchBy)) {
            return QUser.user.username.like("%" + searchQuery + "%");
            //이메일로 검색
        } else if(StringUtils.equals("email", searchBy)) {
            return QUser.user.email.like("%" + searchQuery + "%");
            //주소로 검색
        } else if(StringUtils.equals("my_address", searchBy)){
            return QUser.user.my_address.like("%" + searchQuery + "%");
        }

        return null;
    }

}
