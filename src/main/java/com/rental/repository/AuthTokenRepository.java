package com.rental.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rental.model.AuthToken;
import com.rental.model.UserInfo;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken,UUID>
{
    boolean existsByUserInfo(UserInfo userInfo);
    
    @Modifying(clearAutomatically = true)
    @Query("UPDATE AuthToken a SET a.expiryDate = :newExpiryDate WHERE a.id = :tokenID")
    int updateTokenExpiryDate(@Param("newExpiryDate") LocalDateTime newExpiryDate, @Param("tokenID") UUID tokenID);
}
