package com.rental.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.model.dto.LoginDto;
import com.rental.model.dto.RegistrationDto;

public class TestUtils
{
    public static String getLoginPostBody(LoginDto loginDto) throws JsonProcessingException
    {
        return new ObjectMapper().writeValueAsString(loginDto);
    }
    
    public static HttpHeaders getHttpHeadersForJsonContentType()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
    
    public static String getRegistrationPostBody(RegistrationDto registrationDto) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(registrationDto);
    }
    
    public static HttpHeaders getAuthorizationHeader(String token)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }
}
