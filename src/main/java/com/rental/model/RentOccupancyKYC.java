package com.rental.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.rental.model.identifier.OccupancyIdentifier;

@Entity
@Table(name = "RentOccupancyKYC")
@IdClass(OccupancyIdentifier.class)
public class RentOccupancyKYC
{
    @Id
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private User tenant;
    
    @Id
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "PropertyID")
    private Property property;
    
    public RentOccupancyKYC()
    {
    
    }
    
    public User getTenant()
    {
        return tenant;
    }
    
    public void setTenant(User tenant)
    {
        this.tenant = tenant;
    }
    
    public Property getProperty()
    {
        return property;
    }
    
    public void setProperty(Property property)
    {
        this.property = property;
    }
}
