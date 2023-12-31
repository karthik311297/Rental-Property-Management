package com.rental.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.rental.model.identifier.OccupancyIdentifier;

@Entity
@Table(name = "Occupancy")
@IdClass(OccupancyIdentifier.class)
public class Occupancy
{
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserID")
    private UserInfo tenant;
    
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PropertyID")
    private Property property;
    
    @Column(nullable = false)
    private LocalDateTime occupiedDate;
    
    public Occupancy()
    {
    
    }
    
    public UserInfo getTenant()
    {
        return tenant;
    }
    
    public void setTenant(UserInfo tenant)
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
    
    public LocalDateTime getOccupiedDate()
    {
        return occupiedDate;
    }
    
    public void setOccupiedDate(LocalDateTime occupiedDate)
    {
        this.occupiedDate = occupiedDate;
    }
}
