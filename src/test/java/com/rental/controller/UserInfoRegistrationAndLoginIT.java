package com.rental.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.model.ROLE;
import com.rental.model.dto.LoginDto;
import com.rental.model.dto.RegistrationDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserInfoRegistrationAndLoginIT extends AbstractTransactionalTestNGSpringContextTests
{
    @Autowired
    TestRestTemplate testRestTemplate;
    
    @BeforeClass
    public void setup() throws JsonProcessingException
    {
        HttpHeaders headers = getHttpHeadersForJsonContentType();
        RegistrationDto registrationDto = new RegistrationDto("Karthik", "karthik.gmail.com", LocalDateTime.now(),
                "909909", ROLE.OWNER, "kiyer", "kPassword");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        testRestTemplate.postForEntity("/register",
                new HttpEntity<String>(getRegistrationPostBody(registrationDto, objectMapper), headers), String.class);
    }
    
    private HttpHeaders getHttpHeadersForJsonContentType()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
    
    @Test
    public void shouldReturnAuthenticatedTokenOnSuccessfulLoginForRegisteredUser() throws JsonProcessingException
    {
        
        LoginDto loginDto = new LoginDto("kiyer", "kPassword");
        
        ResponseEntity<String> loginResponse = testRestTemplate.postForEntity("/login",
                new HttpEntity<String>(getLoginPostBody(loginDto), getHttpHeadersForJsonContentType()), String.class);

        String tokenId = loginResponse.getBody();
        Assert.assertEquals(loginResponse.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(tokenId);
    }
    
    @Test
    public void shouldFailRegistrationWhenUsernameOrEmailIdAlreadyExists() throws JsonProcessingException
    {
        HttpHeaders headers = getHttpHeadersForJsonContentType();
        RegistrationDto registrationDto = new RegistrationDto("Karthik", "karthik.gmail.com", LocalDateTime.now(),
                "909909", ROLE.OWNER, "kiyer", "kPassword");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        
        ResponseEntity<String> regResponse =
                testRestTemplate.postForEntity("/register",
                        new HttpEntity<String>(getRegistrationPostBody(registrationDto, objectMapper), headers), String.class);
        
        
        Assert.assertEquals(regResponse.getStatusCode(), HttpStatus.CONFLICT);
    }
    
    @Test
    public void shouldFailLoginForRegisteredUserWhenCredentialsAreInvalid() throws JsonProcessingException
    {
        LoginDto loginDto = new LoginDto("kiyer3", "kPassword");
        
        ResponseEntity<String> loginResponse = testRestTemplate.postForEntity("/login",
                new HttpEntity<String>(getLoginPostBody(loginDto), getHttpHeadersForJsonContentType()), String.class);
        
        Assert.assertEquals(loginResponse.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    public void shouldAllowLoggedInUserToLogout()
    {
        Assert.fail();
    }
    
    @Test
    public void shouldNotAllowANonAuthenticatedUserToCallLogoutEndpoint()
    {
        Assert.fail();
    }
    
    @Test
    public void shouldNotAllowADifferentAuthenticatedUserToDoLogoutForOtherUser()
    {
        Assert.fail();
    }
    
    private String getRegistrationPostBody(RegistrationDto registrationDto, ObjectMapper objectMapper) throws JsonProcessingException
    {
        return objectMapper.writeValueAsString(registrationDto);
    }
    
    private String getLoginPostBody(LoginDto loginDto) throws JsonProcessingException
    {
        return new ObjectMapper().writeValueAsString(loginDto);
    }
}