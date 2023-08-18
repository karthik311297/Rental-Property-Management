package com.rental.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.exception.UserAlreadyExistsExceptions;
import com.rental.model.UserInfo;
import com.rental.model.dto.LoginDto;
import com.rental.model.dto.RegistrationDto;
import com.rental.service.AuthTokenService;
import com.rental.service.UserService;

@RestController
@RequestMapping
public class LoginController
{
    @Autowired
    public UserService userService;
    
    @Autowired
    public AuthTokenService authTokenService;
    
    private final AuthenticationManager authenticationManager;
    
    public LoginController(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto)
    {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        String username = "";
        try
        {
            Authentication authentication = authenticationManager.authenticate(credentials);
            username = ((User)authentication.getPrincipal()).getUsername();
        }
        catch(Exception exception)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect Username or Password");
        }
        UserInfo userInfo = userService.getUserByUserName(username).get();
        return authTokenService.getAuthenticatedSessionToken(userInfo);
    }
    
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationDto registrationDto)
    {
        try
        {
            userService.registerUser(registrationDto);
        }
        catch(UserAlreadyExistsExceptions e)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration Successful");
    }
    
    @GetMapping("/api/checkauth")
    public ResponseEntity<String> ping(Principal principal)
    {
        Principal p = principal;
        return ResponseEntity.status(HttpStatus.OK).body("You are authenticated!");
    }
}
