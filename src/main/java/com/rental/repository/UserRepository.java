package com.rental.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rental.model.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,UUID>
{
    boolean existsByUserName(String userName);
    
    boolean existsByEmailId(String emailId);
    Optional<UserInfo> findByUserName(String userName);
    
    Optional<UserInfo> findByEmailId(String emailId);
}
