package com.rental.model;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Property")
public class Property
{
    @Id
    @GeneratedValue
    @Column(name = "PropertyID")
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String city;
    
    private int rent;
    
    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private UserInfo owner;
    
    public Property()
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
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public int getRent()
    {
        return rent;
    }
    
    public void setRent(int rent)
    {
        this.rent = rent;
    }
    
    public UserInfo getOwner()
    {
        return owner;
    }
    
    public void setOwner(UserInfo owner)
    {
        this.owner = owner;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof Property)) return false;
        Property property = (Property)o;
        return getRent() == property.getRent() && getId().equals(property.getId()) && getName().equals(property.getName()) && getAddress().equals(property.getAddress()) && getCity().equals(property.getCity()) && getOwner().equals(property.getOwner());
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getName(), getAddress(), getCity(), getRent(), getOwner());
    }
}
