package com.rental.dto;

import java.time.LocalDateTime;

public class AuthTokenDto
{
    private String sessionTokenID;
    
    private LocalDateTime expiryDate;
    
    public AuthTokenDto()
    {
    }
    
    public AuthTokenDto(String sessionTokenID, LocalDateTime expiryDate)
    {
        this.sessionTokenID = sessionTokenID;
        this.expiryDate = expiryDate;
    }
    
    public String getSessionTokenID()
    {
        return sessionTokenID;
    }
    
    public void setSessionTokenID(String sessionTokenID)
    {
        this.sessionTokenID = sessionTokenID;
    }
    
    public LocalDateTime getExpiryDate()
    {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDateTime expiryDate)
    {
        this.expiryDate = expiryDate;
    }
}
