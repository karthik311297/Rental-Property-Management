package com.rental.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.exception.UserAlreadyExistsExceptions;
import com.rental.model.UserInfo;
import com.rental.model.dto.RegistrationDto;
import com.rental.repository.UserRepository;

@Service
public class UserService implements UserDetailsService
{
    @Autowired
    public UserRepository userRepository;
    
    @Transactional
    public void registerUser(RegistrationDto registrationDto) throws UserAlreadyExistsExceptions
    {
        if(userRepository.existsByEmailId(registrationDto.getEmailId())
                || userRepository.existsByUserName(registrationDto.getUserName()))
        {
            throw new UserAlreadyExistsExceptions("An account with this username or emailId already exists");
        }
        saveUserInfo(registrationDto);
    }
    
    private void saveUserInfo(RegistrationDto registrationDto)
    {
        UserInfo userInfo = new UserInfo(registrationDto.getName(), registrationDto.getEmailId(),
                registrationDto.getDateOfBirth(), registrationDto.getPhoneNumber(),
                registrationDto.getRole(), registrationDto.getUserName(), encoder().encode(registrationDto.getPassword()));
        userRepository.save(userInfo);
    }
    
    public Optional<UserInfo> getUserByUserName(String username)
    {
        return userRepository.findByUserName(username);
    }
    
    @Bean
    private PasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<UserInfo> theUser = userRepository.findByUserName(username);
        if(!theUser.isPresent())
        {
            throw new UsernameNotFoundException("Invalid username");
        }
        String role = theUser.get().getRole().name();
        return org.springframework.security.core.userdetails.User.builder()
                .username(theUser.get().getUserName())
                .password(theUser.get().getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(role)))
                .build();
    }
}
