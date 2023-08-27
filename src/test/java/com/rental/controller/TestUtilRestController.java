package com.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.dto.AuthTokenDto;
import com.rental.service.AuthTokenService;

@RestController
@RequestMapping
@Profile("test")
public class TestUtilRestController
{
    @Autowired
    private AuthTokenService authTokenService;
    
    @PostMapping("/test/updatetokenexpiry")
    public ResponseEntity<String> updateTokenExpiry(@RequestBody AuthTokenDto authTokenDto)
    {
        int updated = authTokenService.updateTokenExpiryDate(authTokenDto.getExpiryDate(), authTokenDto.getSessionTokenID());
        if(updated == 1) return ResponseEntity.ok("Updated the auth token expiry");
        return ResponseEntity.notFound().build();
    }
}
