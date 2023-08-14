package com.rental.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.model.AuthToken;
import com.rental.model.UserInfo;

public interface AuthTokenRepository extends JpaRepository<AuthToken,UUID>
{
    boolean existsByUserInfo(UserInfo userInfo);
    
    AuthToken findByUserInfo(UserInfo userInfo);
}
