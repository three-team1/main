package com.main.miniproject.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.miniproject.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom{
	Optional<User> findByUsername(String username);
	
	Optional<User> findByProviderIdAndProvider(String providerId, String provider);
}
