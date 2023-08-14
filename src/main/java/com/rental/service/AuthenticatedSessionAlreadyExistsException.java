package com.rental.service;

public class AuthenticatedSessionAlreadyExistsException extends Exception
{
    public AuthenticatedSessionAlreadyExistsException(String message)
    {
        super(message);
    }
}
