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
@Table(name = "User")
public class User
{
    @Id
    @GeneratedValue
    @Column(name = "UserID")
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String emailId;
    
    @Column(nullable = false)
    private LocalDateTime dateOfBirth;
    
    @Column(nullable = false, length = 13)
    private String phoneNumber;
    
    public User()
    {
    
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
    
    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof User)) return false;
        User user = (User)o;
        return getId().equals(user.getId()) && getName().equals(user.getName())
                && getEmailId().equals(user.getEmailId()) && getDateOfBirth().equals(user.getDateOfBirth())
                && getPhoneNumber().equals(user.getPhoneNumber());
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getName(), getEmailId(), getDateOfBirth(), getPhoneNumber());
    }
}
