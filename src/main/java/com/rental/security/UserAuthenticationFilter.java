package com.rental.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class UserAuthenticationFilter extends AbstractAuthenticationProcessingFilter
{
    private static AuthenticationManager authenticationManager;
    
    protected UserAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager)
    {
        super(defaultFilterProcessesUrl, authenticationManager);
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        return null;
    }
}
