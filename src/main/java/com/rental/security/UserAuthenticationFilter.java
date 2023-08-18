package com.rental.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.rental.model.AuthToken;
import com.rental.model.UserInfo;
import com.rental.service.AuthTokenService;

public class UserAuthenticationFilter extends BasicAuthenticationFilter
{
    private AuthTokenService authTokenService;
    private final String authTokenExpiryTimeInMinutes;
    
    public UserAuthenticationFilter(AuthTokenService authTokenService, String authTokenExpiryTimeInMinutes, AuthenticationManager authenticationManager)
    {
        super(authenticationManager);
        this.authTokenExpiryTimeInMinutes = authTokenExpiryTimeInMinutes;
        this.authTokenService = authTokenService;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null)
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Optional<AuthToken> tokenOptional = authTokenService.findByAuthTokenById(authHeader.split(" ")[1]);
        if(!tokenOptional.isPresent() || isTokenExpired(tokenOptional.get()))
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        UserInfo userInfo = tokenOptional.get().getUser();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userInfo.getUserName(), userInfo.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userInfo.getRole().name())));
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }
    
    private boolean isTokenExpired(AuthToken authToken)
    {
        return LocalDateTime.now().isAfter(authToken.getExpiryDate());
    }
    
    private int getAuthTokenExpiryTimeInMinutes()
    {
        return Integer.parseInt(authTokenExpiryTimeInMinutes);
    }
}
