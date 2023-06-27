package com.main.miniproject.user.repository;

import com.main.miniproject.user.dto.UserDtoAdmin;
import com.main.miniproject.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<User> getAdminMemberPage(UserDtoAdmin userDtoAdmin, Pageable pageable);

}
