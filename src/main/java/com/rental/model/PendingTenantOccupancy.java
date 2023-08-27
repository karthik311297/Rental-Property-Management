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
@Table(name = "PendingTenantOccupancy")
@IdClass(OccupancyIdentifier.class)
public class PendingTenantOccupancy
{
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserID")
    private UserInfo tenant;
    
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PropertyID")
    private Property property;
    
    public PendingTenantOccupancy()
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
}
