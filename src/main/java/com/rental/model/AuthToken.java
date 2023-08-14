package com.rental.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sessiontoken")
public class AuthToken
{
    @Id
    @GeneratedValue
    @Column(nullable = false, name = "sessionID")
    private UUID id;
    
    @Column(nullable = false)
    private LocalDateTime expiryDate;
    
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private UserInfo userInfo;
    
    public AuthToken()
    {
    
    }
    
    public AuthToken(LocalDateTime expiryDate, UserInfo userInfo)
    {
        this.expiryDate = expiryDate;
        this.userInfo = userInfo;
    }
    
    public UUID getId()
    {
        return id;
    }
    
    public void setId(UUID id)
    {
        this.id = id;
    }
    
    public LocalDateTime getExpiryDate()
    {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDateTime expiryDate)
    {
        this.expiryDate = expiryDate;
    }
    
    public UserInfo getUser()
    {
        return userInfo;
    }
    
    public void setUser(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }
}
