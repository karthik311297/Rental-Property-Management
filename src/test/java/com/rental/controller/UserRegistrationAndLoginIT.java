package com.rental.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.dto.AuthTokenDto;
import com.rental.model.ROLE;
import com.rental.model.dto.LoginDto;
import com.rental.model.dto.RegistrationDto;
import com.rental.service.AuthTokenService;
import com.rental.utils.TestUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRegistrationAndLoginIT extends AbstractTransactionalTestNGSpringContextTests
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
                new HttpEntity<String>(TestUtils.getLoginPostBody(loginDto), TestUtils.getHttpHeadersForJsonContentType()), String.class);
        
        String tokenId = loginResponse.getBody();
        Assert.assertEquals(loginResponse.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(tokenId);
    }
    
    @Test
    public void shouldFailRegistrationWhenUsernameOrEmailIdAlreadyExists() throws JsonProcessingException
    {
        registerUser();
        HttpHeaders headers = TestUtils.getHttpHeadersForJsonContentType();
        RegistrationDto registrationDto = new RegistrationDto("Karthik", "karthik.gmail.com", "31 Dec 1997",
                "909909", ROLE.OWNER, "kiyer", "kPassword");
        
        ResponseEntity<String> regResponse =
                testRestTemplate.postForEntity("/register",
                        new HttpEntity<String>(TestUtils.getRegistrationPostBody(registrationDto), headers), String.class);
        
        
        Assert.assertEquals(regResponse.getStatusCode(), HttpStatus.CONFLICT);
    }
    
    @Test
    public void shouldFailLoginWhenCredentialsAreInvalid() throws JsonProcessingException
    {
        registerUser();
        LoginDto loginDto = new LoginDto("kiyer3", "kPassword");
        
        ResponseEntity<String> loginResponse = testRestTemplate.postForEntity("/login",
                new HttpEntity<String>(TestUtils.getLoginPostBody(loginDto), TestUtils.getHttpHeadersForJsonContentType()), String.class);
        
        Assert.assertEquals(loginResponse.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    public void shouldAllowToMakeCallToProtectedEndpointsWithValidAuthenticatedToken() throws JsonProcessingException
    {
        registerUser();
        LoginDto loginDto = new LoginDto("kiyer", "kPassword");
        ResponseEntity<String> loginResponse = testRestTemplate.postForEntity("/login",
                new HttpEntity<String>(TestUtils.getLoginPostBody(loginDto), TestUtils.getHttpHeadersForJsonContentType()), String.class);
        String tokenId = loginResponse.getBody();
        
        ResponseEntity<String> response = testRestTemplate.exchange("/api/checkauth", HttpMethod.GET,
                new HttpEntity<>(TestUtils.getAuthorizationHeader(tokenId)), String.class);
        
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    
    @Test
    public void shouldNotAllowToMakeCallToProtectedEndpointsWithExpiredToken() throws JsonProcessingException
    {
        registerUser();
        LoginDto loginDto = new LoginDto("kiyer", "kPassword");
        ResponseEntity<String> loginResponse = testRestTemplate.postForEntity("/login",
                new HttpEntity<String>(TestUtils.getLoginPostBody(loginDto), TestUtils.getHttpHeadersForJsonContentType()), String.class);
        String tokenId = loginResponse.getBody();
        expireAuthToken(tokenId);
        
        ResponseEntity<String> response = testRestTemplate.exchange("/api/checkauth", HttpMethod.GET,
                new HttpEntity<>(TestUtils.getAuthorizationHeader(tokenId)), String.class);
        
        Assert.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
        Assert.assertFalse(authTokenService.findAuthTokenById(tokenId).isPresent());
    }
    
    @Test
    public void shouldNotAllowToMakeCallToProtectedEndpointsWithoutAuthenticatedToken() throws JsonProcessingException
    {
        registerUser();
        ResponseEntity<String> response = testRestTemplate.getForEntity("/api/checkauth", String.class);
        
        Assert.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }
    
    private int expireAuthToken(String tokenId) throws JsonProcessingException
    {
        AuthTokenDto authTokenDto = new AuthTokenDto(tokenId, LocalDateTime.now().minusMinutes(4));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        ResponseEntity<String> response = testRestTemplate.postForEntity("/test/updatetokenexpiry",
                new HttpEntity<String>(objectMapper.writeValueAsString(authTokenDto), TestUtils.getHttpHeadersForJsonContentType()), String.class);
        return response.getStatusCodeValue();
    }
    
    private void registerUser() throws JsonProcessingException
    {
        HttpHeaders headers = TestUtils.getHttpHeadersForJsonContentType();
        RegistrationDto registrationDto = new RegistrationDto("Karthik", "karthik.gmail.com", "31 Dec 1997",
                "909909", ROLE.OWNER, "kiyer", "kPassword");
        
        testRestTemplate.postForEntity("/register",
                new HttpEntity<String>(TestUtils.getRegistrationPostBody(registrationDto), headers), String.class);
    }
}