package com.rental.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rental.model.AuthToken;
import com.rental.model.UserInfo;
import com.rental.repository.AuthTokenRepository;

@Service
public class AuthTokenService
{
    @Autowired
    public AuthTokenRepository authTokenRepository;
    
    @Value("${auth.token.expiry.minutes}")
    private String authTokenExpiryTimeInMinutes;
    
    public ResponseEntity<String> getAuthenticatedSessionToken(UserInfo userInfo)
    {
        try
        {
            String token = getAuthenticatedUserToken(userInfo);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }
        catch(AuthenticatedSessionAlreadyExistsException e)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    
    private String getAuthenticatedUserToken(UserInfo userInfo) throws AuthenticatedSessionAlreadyExistsException
    {
        AuthToken token;
        if(isUserPreviouslyAuthenticated(userInfo))
        {
            throw new AuthenticatedSessionAlreadyExistsException("You already have an existing authenticated Session");
        }
        else
        {
            token = getFreshAuthToken(userInfo);
        }
        return token.getId().toString();
    }
    
//    private boolean isTokenExpired(AuthToken authToken)
//    {
//        return Duration.between(LocalDateTime.now(), authToken.getExpiryDate()).toMinutes() > getAuthTokenExpiryTimeInMinutes();
//    }
    
    private AuthToken getFreshAuthToken(UserInfo userInfo)
    {
        AuthToken token;
        token = authTokenRepository
                .save(new AuthToken(LocalDateTime.now().plusMinutes(getAuthTokenExpiryTimeInMinutes()), userInfo));
        return token;
    }
    
    private int getAuthTokenExpiryTimeInMinutes()
    {
        return Integer.parseInt(authTokenExpiryTimeInMinutes);
    }
    
    private boolean isUserPreviouslyAuthenticated(UserInfo userInfo)
    {
        return authTokenRepository.existsByUserInfo(userInfo);
    }
    
}
