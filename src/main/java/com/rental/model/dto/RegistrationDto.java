package com.rental.model.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import com.rental.model.ROLE;

public class RegistrationDto
{
    private String name;
    
    private String emailId;
    
    private LocalDateTime dateOfBirth;
    
    private String phoneNumber;
    
    private ROLE role;
    
    private String userName;
    
    private String password;
    
    public RegistrationDto()
    {
    
    }
    
    public RegistrationDto(String name, String emailId, LocalDateTime dateOfBirth, String phoneNumber, ROLE role, String userName, String password)
    {
        this.name = name;
        this.emailId = emailId;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.userName = userName;
        this.password = password;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getEmailId()
    {
        return emailId;
    }
    
    public void setEmailId(String emailId)
    {
        this.emailId = emailId;
    }
    
    public LocalDateTime getDateOfBirth()
    {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDateTime dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    
    public ROLE getRole()
    {
        return role;
    }
    
    public void setRole(ROLE role)
    {
        this.role = role;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
}
