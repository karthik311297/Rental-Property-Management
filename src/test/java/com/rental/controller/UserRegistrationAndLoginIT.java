package com.rental.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.model.ROLE;
import com.rental.model.dto.LoginDto;
import com.rental.model.dto.RegistrationDto;
import com.rental.service.AuthTokenService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserInfoRegistrationAndLoginIT extends AbstractTransactionalTestNGSpringContextTests
{
    @Autowired
    private TestRestTemplate testRestTemplate;
    
    @Autowired
    private AuthTokenService authTokenService;
    
    @Test
    public void shouldReturnAuthenticatedTokenOnSuccessfulLoginForRegisteredUser() throws JsonProcessingException
    {
        registerUser();
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
        registerUser();
        HttpHeaders headers = getHttpHeadersForJsonContentType();
        RegistrationDto registrationDto = new RegistrationDto("Karthik", "karthik.gmail.com", "31 Dec 1997",
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
        registerUser();
        LoginDto loginDto = new LoginDto("kiyer3", "kPassword");
        
        ResponseEntity<String> loginResponse = testRestTemplate.postForEntity("/login",
                new HttpEntity<String>(getLoginPostBody(loginDto), getHttpHeadersForJsonContentType()), String.class);
        
        Assert.assertEquals(loginResponse.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    public void shouldAllowToMakeCallToProtectedEndpointsWithValidAuthenticatedToken() throws JsonProcessingException
    {
        registerUser();
        LoginDto loginDto = new LoginDto("kiyer", "kPassword");
        ResponseEntity<String> loginResponse = testRestTemplate.postForEntity("/login",
                new HttpEntity<String>(getLoginPostBody(loginDto), getHttpHeadersForJsonContentType()), String.class);
        String tokenId = loginResponse.getBody();
        
        ResponseEntity<String> response = testRestTemplate.exchange("/api/checkauth", HttpMethod.GET, new HttpEntity<>(getAuthorizationHeader(tokenId)), String.class);
        
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    
    @Test
    public void shouldNotAllowToMakeCallToProtectedEndpointsWithExpiredToken() throws JsonProcessingException
    {
        registerUser();
        LoginDto loginDto = new LoginDto("kiyer", "kPassword");
        ResponseEntity<String> loginResponse = testRestTemplate.postForEntity("/login",
                new HttpEntity<String>(getLoginPostBody(loginDto), getHttpHeadersForJsonContentType()), String.class);
        String tokenId = loginResponse.getBody();
        expireAuthToken(tokenId);
        
        ResponseEntity<String> response = testRestTemplate.exchange("/api/checkauth", HttpMethod.GET, new HttpEntity<>(getAuthorizationHeader(tokenId)), String.class);
        
        Assert.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }
    
    @Test
    public void shouldNotAllowToMakeCallToProtectedEndpointsWithoutAuthenticatedToken() throws JsonProcessingException
    {
        registerUser();
        ResponseEntity<String> response = testRestTemplate.getForEntity("/api/checkauth", String.class);
        
        Assert.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }
    
    private void expireAuthToken(String tokenId)
    {
        authTokenService.updateTokenExpiryDate(LocalDateTime.now().minusMinutes(4), tokenId);
    }
    
    private void registerUser() throws JsonProcessingException
    {
        HttpHeaders headers = getHttpHeadersForJsonContentType();
        RegistrationDto registrationDto = new RegistrationDto("Karthik", "karthik.gmail.com", "31 Dec 1997",
                "909909", ROLE.OWNER, "kiyer", "kPassword");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        testRestTemplate.postForEntity("/register",
                new HttpEntity<String>(getRegistrationPostBody(registrationDto, objectMapper), headers), String.class);
    }
    
    private HttpHeaders getAuthorizationHeader(String token)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }
    
    private HttpHeaders getHttpHeadersForJsonContentType()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
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