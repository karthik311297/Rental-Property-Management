package com.rental.model.identifier;

import java.io.Serializable;
import java.util.Objects;

import com.rental.model.Property;
import com.rental.model.UserInfo;

public class OccupancyIdentifier implements Serializable
{
    private UserInfo tenant;
    
    private Property property;
    
    public OccupancyIdentifier()
    {
    
    }
    
    public OccupancyIdentifier(UserInfo tenant, Property property)
    {
        this.tenant = tenant;
        this.property = property;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof OccupancyIdentifier)) return false;
        OccupancyIdentifier that = (OccupancyIdentifier)o;
        return tenant.equals(that.tenant) && property.equals(that.property);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(tenant, property);
    }
}
