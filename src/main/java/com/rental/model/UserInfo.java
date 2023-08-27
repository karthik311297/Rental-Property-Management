package com.rental.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class UserInfo
{
    @Id
    @GeneratedValue
    @Column(name = "UserID", columnDefinition = "uuid")
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String emailId;
    
    @Column(nullable = false)
    private String dateOfBirth;
    
    @Column(nullable = false, length = 13)
    private String phoneNumber;
    
    @Column(nullable = false)
    private ROLE role;
    
    @Column(nullable = false)
    private String userName;
    
    @Column(nullable = false)
    private String password;
    
    public UserInfo()
    {
    
    }
    
    public UserInfo(String name, String emailId, String dateOfBirth, String phoneNumber, ROLE role, String userName, String password)
    {
        this.name = name;
        this.emailId = emailId;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.userName = userName;
        this.password = password;
    }
    
    public UUID getId()
    {
        return id;
    }
    
    public void setId(UUID id)
    {
        this.id = id;
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
    
    public String getDateOfBirth()
    {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(String dateOfBirth)
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
    
    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof UserInfo)) return false;
        UserInfo userInfo = (UserInfo)o;
        return getId().equals(userInfo.getId()) && getName().equals(userInfo.getName())
                && getEmailId().equals(userInfo.getEmailId()) && getDateOfBirth().equals(userInfo.getDateOfBirth())
                && getPhoneNumber().equals(userInfo.getPhoneNumber()) && getRole() == userInfo.getRole() &&
                getUserName().equals(userInfo.getUserName()) && getPassword().equals(userInfo.getPassword());
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getName(), getEmailId(), getDateOfBirth(), getPhoneNumber(), getRole(), getUserName(), getPassword());
    }
}
